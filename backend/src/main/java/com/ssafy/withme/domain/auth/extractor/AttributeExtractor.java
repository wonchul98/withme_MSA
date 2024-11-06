package com.ssafy.withme.domain.auth.extractor;

import java.util.Map;

public interface ProviderAttributeExtractor {
    String getUsername(Map<String, Object> attributes);
    String getImageUrl(Map<String, Object> attributes);
}
