package com.pllis.mylog.service;

import java.net.URI;
import java.util.Collections;
import java.util.List;

import com.pllis.mylog.repository.BookRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pllis.mylog.dto.BookDto;
import com.pllis.mylog.dto.CommonDto;
import com.pllis.mylog.domain.MyBook;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiService {

    private final BookRepository bookRepository;


    @Value("${aladin.ttbKey}")
    private String ttbKey;

    // //item 객체 생성 후 itemDto의 내용을 item에 저장
    // public void saveItem(BookDto itemDto){
    //    Book item = Book.builder()
    //            .bookTitle(itemDto.getBookTitle())
    //            .build();
    //    bookRepository.save(item); //bookRepository를 통해 item 저장
    // }

    // //bookRepository를 통해 id에 해당하는 item을 찾아서 반환
    // public BookDto findItemById(Long id){
    //     Book item = bookRepository.findById(id);

    //     return BookDto.builder()
    //             .id(item.getId())
    //             .bookTitle(item.getBookTitle())
    //             .build(); // itemDto 반환
    // }



    /**
     * 네이버 책 API 호출
     * @return
     */
    // public CommonDto.ListResponse<BookDto.BookSearchResponse> getSearchBookList(){

    //     String clientId = "arfLd8NTbndr22CjIcVT"; //애플리케이션 클라이언트 아이디
    //     String clientSecret = "RMI0TMsXrc"; //애플리케이션 클라이언트 시크릿

    //     //String apiURL = "https://openapi.naver.com/v1/search/blog?query=" + text;    // JSON 결과
    //     URI uri = UriComponentsBuilder
    //             .fromUriString("https://openapi.naver.com")
    //             .path("/v1/search/book.json")
    //             .queryParam("query", "소년이 온다")
    //             .queryParam("display", 10)
    //             .queryParam("start", 1)
    //             .queryParam("sort", "sim")
    //             .encode()
    //             .build()
    //             .toUri();

    //     // Spring 요청 제공 클래스
    //     RequestEntity<Void> req = RequestEntity
    //                             .get(uri)
    //                             .header("X-Naver-Client-Id", clientId)
    //                             .header("X-Naver-Client-Secret", clientSecret)
    //                             .build();
    //     // Spring 제공 restTemplate
    //     RestTemplate restTemplate = new RestTemplate();
    //     ResponseEntity<String> resp = restTemplate.exchange(req, String.class);

    //     // JSON 파싱 (Json 문자열을 객체로 만듦, 문서화)
    //     ObjectMapper om = new ObjectMapper();
    //     BookDto.BookSearchResponse resultVO = null;

    //     try {
    //         resultVO = om.readValue(resp.getBody(), BookDto.BookSearchResponse.class);
    //     } catch (JsonMappingException e) {
    //         e.printStackTrace();
    //     } catch (JsonProcessingException e) {
    //         e.printStackTrace();
    //     }

    //     // 리턴 객체 구성
    //     return CommonDto.ListResponse.<BookDto.BookSearchResponse>builder()
    //             .list(Collections.singletonList(resultVO)) // resultVO를 리스트로 감싸서 list에 추가
    //             .build();
    // }


    /**
     * 알라딘 책 API 호출
     * @return
     */
    public CommonDto.ListResponse<BookDto.BookSearchResponse> getSearchBookList(){

        URI uri = UriComponentsBuilder
                .fromUriString("http://www.aladin.co.kr")
                .path("/ttb/api/ItemSearch.aspx")
                .queryParam("ttbkey", ttbKey)
                .queryParam("Query", "댄 애리얼리 미스빌리프")
                .queryParam("QueryType", "Title")
                .queryParam("SearchTarget", "All")
                .queryParam("MaxResults", 10)
                .queryParam("start", 1)
                .queryParam("sort", "Title")
                .queryParam("Cover", "MidBig")
                .queryParam("output", "js")
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

    // public void updateItemById(Long id, BookDto itemDto){

    //     Book findItem = bookRepository.findById(id); // bookRepository를 통해 id에 해당하는 item을 찾아서 반환
    //     findItem.updateItem(itemDto.getBookTitle()); // item의 내용을 수정

    //     bookRepository.updateById(id, findItem); // bookRepository를 통해 id에 해당하는 item을 찾아서 내용 수정


    // }

    // public void deleteItemById(Long id){
    //     bookRepository.deleteById(id); //bookRepository를 통해 id에 해당하는 item을 찾아서 삭제
    // }

}