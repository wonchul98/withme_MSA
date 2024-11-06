package com.ssafy.withme.domain.auth.extractor;

import com.ssafy.withme.domain.member.entity.Provider;

import java.util.HashMap;
import java.util.Map;

public class ProvideAttributeExtractorFactory {
    private static final Map<Provider, ProviderAttributeExtractor> mappers = new HashMap<>();

    static {
        mappers.put(Provider.GITHUB, new GithubAttributeExtractor());
        mappers.put(Provider.GITLAB, new GitlabAttributeExtractor());
    }

    public static ProviderAttributeExtractor getMapper(Provider provider) {
        return mappers.get(provider);
    }
}
