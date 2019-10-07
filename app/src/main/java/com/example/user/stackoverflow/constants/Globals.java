package com.example.user.stackoverflow.constants;

import com.example.user.stackoverflow.database.AppDatabase;
import com.example.user.stackoverflow.model.User;

import java.util.ArrayList;
import java.util.List;

public class Globals {
    public static AppDatabase myDatabase;
    public static List<User> dbUser = new ArrayList<>();
}
