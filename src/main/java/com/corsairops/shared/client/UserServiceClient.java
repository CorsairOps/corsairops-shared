package com.corsairops.shared.client;

import com.corsairops.shared.dto.User;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;

import java.util.List;

public interface UserServiceClient {

    @GetExchange("/api/users/auth")
    User getAuthUser(@RequestHeader("X-User-Id") String userId) ;

    @GetExchange("/api/users")
    List<User> getAllUsers();

    @GetExchange("/api/users/ids")
    List<User> getUsersByIds(@RequestParam("ids") String ids, @RequestParam(value="allowEmpty", defaultValue = "false") boolean allowEmpty);

    @GetExchange("/api/users/{id}")
    User getUserById(@PathVariable("id") String id);

    @GetExchange("/api/users/count")
    Integer getUserCount();
}