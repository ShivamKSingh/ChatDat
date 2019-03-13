package com.example.chatdat.Utilities

const val BASE_URL = "https://chatdat.herokuapp.com/v1/"

const val SOCKET_URL = "https://chatdat.herokuapp.com/"
const val URL_REGISTER = "${BASE_URL}account/register"
const val URL_LOGIN = "${BASE_URL}account/login"
const val URL_CREATE_USER = "${BASE_URL}user/add"
const val URL_GET_USER = "${BASE_URL}user/byEmail/"

//Broadcast Constants
const val BROADCAT_USER_DATA_CHANGE = "BROADCAST_USER_DATA_CHANGE"