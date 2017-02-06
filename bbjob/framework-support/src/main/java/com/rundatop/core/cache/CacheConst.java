package com.rundatop.core.cache;

public class CacheConst {
	public final static String CACHE_COURSE_LIST = "cache_course_list";
	public final static String CACHE_COURSE = "cache_course";

	public static final String CONNECT_FLAG = ":";

	/**
	 * Categories(Domain)
	 */
	public static final String CACHE_CATEGORIES_SERVER = "categories_server";
	/**
	 * 一级门类ids(即那九个大类)
	 */
	public static final String CACHE_CATEGORIES_PARENTIDS = "categories_parentids";

	/**
	 * SiteApps(Domain)
	 */
	public static final String CACHE_SITEAPP_SERVER = "siteapp_server";
	public static final String CACHE_SITEAPP = "siteapp";
	public static final String CACHE_SITEAPP_LIST = "siteapp_list";
	public static final String CACHE_SITEAPP_VERSION = "siteapp_version";
	/**
	 * <[appId/appSecret],id>
	 */
	public static final String CACHE_SITEAPP_ID = "siteapp_id";
	/**
	 * SiteAppTokens(Domain)
	 */
	public static final String CACHE_TOKEN_SERVER = "token_server";
	public static final String CACHE_ACCESS_TOKEN = "access_token";
	public static final String CACHE_ACCESS_TOKEN_LIST = "access_token_list";
	/**
	 * SiteAppDataresourceAuthorize(Domain)
	 */
	public static final String CACHE_SITEAPPID_CATEGORIESIDS_SERVER = "siteappid_categoriesids_server";
	public static final String CACHE_CATEGORIESIDS = "categoriesids";

	/***
	 * 标签的缓存
	 * */
	public final static String CACHE_TAGS = "cache_tags";
	public final static String CACHE_BOOKS = "cache_books";
	/**
	 * 门类缓存
	 * */
	public static final String CACHE_CATEGORIES = "categories";
	
	/**
	 * 分站门类缓存
	 * */
	public static final String CACHE_CATEGORIES_SUB = "categories_sub";
}
