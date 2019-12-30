package ru.recipebook;

import ru.recipebook.model.Role;
import ru.recipebook.model.User;

import java.util.Collections;
import java.util.Date;

import static ru.recipebook.model.AbstractIdEntity.USER_SEQ;

public class UserTestData {
    public static final int USER_ID = USER_SEQ;
    public static final int ADMIN_ID = USER_SEQ + 1;

    public static final User USER = new User(USER_ID, "User", "user@yandex.ru", "password", Role.ROLE_USER);
    public static final User ADMIN = new User(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Role.ROLE_USER, Role.ROLE_ADMIN);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.ROLE_USER));
    }

    public static User getUpdated() {
        User updated = new User(USER);
        updated.setName("UpdatedName");
        updated.setRoles(Collections.singletonList(Role.ROLE_ADMIN));
        return updated;
    }

    public static TestMatchers<User> USER_MATCHERS = TestMatchers.useFieldsComparator(User.class, "registered", "recipes", "password");
}
