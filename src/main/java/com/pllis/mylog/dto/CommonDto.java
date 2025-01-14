package com.pllis.mylog.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommonDto {

    @Builder
	public record ListRequest(
		@NotNull
		Integer offset,

		@NotNull
		Integer limit,

		@Null
		String searchKey,

		@Null
		String searchValue
	) {}

	@Builder
	public record ListResponse<T>(
		List<T> list,
		Integer count
	) {}

	@Builder
	public record InfoResponse<T>(
		T info
	) {}

	@Builder
	public record trueResponse(
		Boolean isTrue
	) {}
}
