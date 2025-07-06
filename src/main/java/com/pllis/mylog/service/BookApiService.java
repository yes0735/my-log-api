package com.pllis.mylog.service;

import java.net.URI;
import java.util.Collections;

import com.pllis.mylog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pllis.mylog.dto.BookDto;
import com.pllis.mylog.dto.CommonDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookApiService {

    @Value("${aladin.ttbKey}")
    private String ttbKey;
    @Value("${aladin.fromUriString}")
    private String fromUriString;
    @Value("${aladin.path}")
    private String path;

    /**
     * 알라딘 책 API 호출
     * @return
     */
    public CommonDto.ListResponse<BookDto.BookSearchResponse> getSearchBookList(String searchType,
                                                                                String searchValue) {

        URI uri = UriComponentsBuilder
                .fromUriString(fromUriString)
                .path(path)
                .queryParam("ttbkey", ttbKey)
                .queryParam("Query", searchValue) // 검색어
                .queryParam("QueryType", searchType) // 검색어 종류 (기본값: 제목+저자)
                .queryParam("SearchTarget", "All") // 검색 대상 Mall (기본값: 도서) -> 도서,외국도서,음반,DVD, 중고샵, 전자책 (모든 타켓 ALL로 설정)
                .queryParam("MaxResults", 100) // 검색결과 한 페이지당 최대 출력 개수 (기본값: 10)
                .queryParam("start", 1) // 검색결과 시작페이지
                .queryParam("sort", "Accuracy") // 정렬순서 (기본값: 관련도)
                .queryParam("Cover", "Mini") // 표지 이미지 크기
                .queryParam("output", "js") // 출력방법 (JSON방식)
                .queryParam("Version", "20131101")
                .encode()
                .build()
                .toUri();

        // Spring 요청 제공 클래스
        RequestEntity<Void> req = RequestEntity
                                .get(uri)
                                .build();
        // Spring 제공 restTemplate
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

        // JSON 파싱 (Json 문자열을 객체로 만듦, 문서화)
        ObjectMapper om = new ObjectMapper();
        BookDto.BookSearchResponse resultVO = null;

        try {
            resultVO = om.readValue(resp.getBody(), BookDto.BookSearchResponse.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // 리턴 객체 구성
        return CommonDto.ListResponse.<BookDto.BookSearchResponse>builder()
                .list(Collections.singletonList(resultVO)) // resultVO를 리스트로 감싸서 list에 추가
                .build();
    }

}