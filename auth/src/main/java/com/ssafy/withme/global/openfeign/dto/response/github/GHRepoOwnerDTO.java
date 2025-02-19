package com.ssafy.withme.global.openfeign.dto.response.github;

public record GHRepoOwnerDTO(
        String login,
        Integer id,
        String node_id,
        String avatar_url,
        String gravatar_id,
        String url,
        String html_url,
        String followers_url,
        String following_url,
        String gists_url,
        String starred_url,
        String subscriptions_url,
        String organizations_url,
        String repos_url,
        String events_url,
        String received_events_url,
        String type,
        Boolean site_admin
) {
}
