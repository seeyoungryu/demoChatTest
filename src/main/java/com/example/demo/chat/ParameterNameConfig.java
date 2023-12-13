package com.example.withdogandcat.domain.chat;

public class ParameterNameConfig {
    public static class Message {
        public static final String MESSAGE = "msg";
        public static final String TOkEN = "token";
        public static final String REFRESH_TOKEN = "refresh_token";
        public static final String STATUS_CODE = "statusCode";
        public static final String IS_FIRST = "first";
    }

    public static class Member {
        public static final String ID = "member_id";
        public static final String USERNAME = "member_username";
        public static final String NICKNAME = "member_nickname";
        public static final String IMAGE = "member_image";
    }

    public static class Item {
        public static final String ID = "item_id";
        public static final String CREATED_AT = "item_created_at";
        public static final String COMMENT = "item_comment";
        public static final String MAIN_IMAGE = "item_main_image";
        public static final String SUB_IMAGE = "item_sub_image";
        public static final String IMAGES = "item_image_list";

        public static final String NAME = "item_name";
        public static final String STATE = "item_state";
        public static final String PRICE = "item_price";
        public static final String WITH_DELIVERY_FEE = "item_with_delivery_fee";
    }

    public static class Category {
        public static final String ID = "category_id";
        public static final String NAME = "category_name";
    }

    public static class CategoryLarge {
        public static final String ID = "category_l_id";
        public static final String NAME = "category_l_name";
        public static final String CHILDREN = "children";
    }

    public static class CategoryMiddle {
        public static final String ID = "category_m_id";
        public static final String NAME = "category_m_name";
    }

    public static class Shop {
        public static final String ID = "shop_id";
        public static final String NAME = "shop_name";
        public static final String INTRO = "shop_intro";
        public static final String SELLER_SHOP_ID = "seller_shop_id";
        public static final String CONSUMER_SHOP_ID = "consumer_shop_id";
        public static final String SELLER_SHOP_NAME = "seller_shop_name";
        public static final String CONSUMER_SHOP_NAME = "consumer_shop_name";
    }

    public static class Follow {
        public static final String IS_FOLLOWING = "is_following";
        public static final String NUM_FOLLOWERS = "num_followers";
        public static final String NUM_FOLLOWINGS = "num_followings";
    }

    public static class Wish {
        public static final String IS_WISHING = "is_wishing";
    }

    public static class Review {
        public static final String ID = "review_id";
        public static final String CREATED_AT = "review_created_at";
        public static final String COMMENT = "review_comment";
        public static final String RATING = "review_rating";
        public static final String RATING_AVG = "review_rating_avg";
        public static final String IS_REVIEW_WRITTEN = "is_review_written";
    }

    public static class Location {
        public static final String NAME = "location_name";
        public static final String LATITUDE = "location_latitude";
        public static final String LONGITUDE = "location_longitude";
    }

    public static class ChatRoom {
        public static final String ID = "chatroom_id";
        public static final String NAME = "chatroom_name";
        public static final String SENDER = "chatroom_sender";
        public static final String SELLER = "chatroom_seller_name";
        public static final String CONSUMER = "chatroom_consumer_name";
        public static final String SELLER_IMAGE = "chatroom_seller_image";
        public static final String CONSUMER_IMAGE = "chatroom_consumer_image";
    }

    public static class ChatMessage {
        public static final String CHATMESSAGE = "chat_message";
        public static final String TYPE = "chat_type";
        public static final String CREATED_AT = "chat_created_at";
    }
}
