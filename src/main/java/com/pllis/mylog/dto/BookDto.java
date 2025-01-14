package com.pllis.mylog.dto;

import java.util.Date;
import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * DTO? 각 계층 간을 이동할 때 데이터를 전달해주는 클래스
 */
@Data
@AllArgsConstructor
public class BookDto {

	@Builder
	public record BookInfoResponse(
//		@Schema(title = "책PK", description = "책번호")
        Integer myBookNo,
        Integer userNo,
        String bookTitle,
        String author,
        String publisher,
        String category,
        String bookImageLink,
        String collectionType,
        String readStatus,
        Date readStartDate,
        Date readEndDate,
        Integer totalPage,
        Integer readPage,
        Integer scope,
        Date registrationDatetime,
        Date updateDatetime
	) {}

    @Builder
	public record SeriesInfo(
        Integer seriesId,
        String seriesLink,
        String seriesName
	) {}

    @Builder
	public record SubInfo(
        List<PaperBookList> paperBookList
	) {}

    @Builder
	public record PaperBookList(
        Integer itemId,
        String isbn,
        String isbn13,
        Integer priceSales,
        String link
	) {}

    @Builder
	public record BookSearchInfo(
        String title,
        String link,
        String author,
        Date pubDate,
        String description,
        String isbn,
        String isbn13,
        Integer itemId,
        Integer priceSales,
        Integer priceStandard,
        String mallType,
        String stockStatus,
        Integer mileage,
        String cover,
        Integer categoryId,
        String categoryName,
        String publisher,
        Integer salesPoint,
        Boolean adult,
        Boolean fixedPrice,
        Integer customerReviewRank,
        SeriesInfo seriesInfo,
        SubInfo subInfo
	) {}

    @Builder
	public record BookSearchResponse(
        @Schema(title = "API Version")
        String version,

        @Schema(title = "logo")
        String logo,

        @Schema(title = "API 결과의 제목")
        String title,

        @Schema(title = "API 결과와 관련된 알라딘 페이지 URL 주소")
        String link,

        @Schema(title = "pubDate")
        Date pubDate,

        @Schema(title = "totalResults")
        Integer totalResults,

        @Schema(title = "startIndex")
        Integer startIndex,

        @Schema(title = "itemsPerPage")
        Integer itemsPerPage,

        @Schema(title = "query")
        String query,

        @Schema(title = "searchCategoryId")
        Integer searchCategoryId,

        @Schema(title = "searchCategoryName")
        String searchCategoryName,

        @Schema(title = "책 검색 정보 리스트")
        List<BookSearchInfo> item
	) {}

    // @Builder
	// public record BookSearchInfo(
    //     @Schema(title = "책제목")
    //     String title,

    //     @Schema(title = "책제목")
    //     String link,

    //     @Schema(title = "책제목")
    //     String image,

    //     @Schema(title = "책제목")
    //     String author,

    //     @Schema(title = "책제목")
    //     String discount,

    //     @Schema(title = "책제목")
    //     String publisher,

    //     @Schema(title = "책제목")
    //     String pubdate,

    //     @Schema(title = "책제목")
    //     String isbn,

    //     @Schema(title = "책제목")
    //     String description
	// ) {}

    // @Builder
	// public record BookSearchResponse(
    //     @Schema(title = "책제목")
    //     String lastBuildDate,

    //     @Schema(title = "책제목")
    //     int total,

    //     @Schema(title = "책제목")
    //     int start,

    //     @Schema(title = "책제목")
    //     int display,

    //     @Schema(title = "책제목")
    //     List<BookSearchInfo> items
	// ) {}
}

