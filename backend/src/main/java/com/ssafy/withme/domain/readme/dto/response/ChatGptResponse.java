package com.ssafy.withme.domain.readme.dto.response;

import java.util.List;
import java.util.Map;

public record ChatGptResponse(
        List<Map<String, String>> choices
) {}
