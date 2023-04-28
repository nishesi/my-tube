package ru.itis.MyTube.controllers;

public class UrlPatterns {
    public static final String PRIVATE = "";
    public static final String VIDEO = "/video";
    public static final String PRIVATE_VIDEO_UPLOAD = PRIVATE + VIDEO + "/upload";
    public static final String PRIVATE_VIDEO_UPDATE = PRIVATE_VIDEO_UPLOAD + "/update";
    public static final String PRIVATE_VIDEO_DELETE = PRIVATE_VIDEO_UPLOAD + "/delete";
    public static final String CHANNEL = "/channel";
    public static final String PRIVATE_CHANNEL_CREATE = PRIVATE + CHANNEL + "/create";
    public static final String PRIVATE_USER_UPDATE = PRIVATE + "/user/update";
    public static final String PRIVATE_USER_EXIT = "/logout";
    public static final String AUTHENTICATION_PAGE = "/login";
    public static final String REGISTRATION_PAGE = "/user/register";
    public static final String PRIVATE_REACTION = PRIVATE + "/reaction";
    public static final String PRIVATE_SUBSCRIBE = PRIVATE + "/subscribe";
    public static final String SEARCH_PAGE = "/search";
    public static final String RESOURCE = "/resource";
}
