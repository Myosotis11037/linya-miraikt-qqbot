package com.linya.mirai.database

class Bilibili{
    //视频表达式
    val bvFind = "BV1[1-9A-NP-Za-km-z]{9}".toRegex()
    val avFind = "(av|AV)[1-9]\\d{0,18}".toRegex()

    //b23短链接
    val b23Find = "(https?://b23.tv/\\S{6})".toRegex()

    //格式化防大量回车
    val deleteEnter = "(\\cJ\\cJ)+".toRegex()

    //东方视频周榜
    val touhouFormat = "(?<=\"list\":\\[).*?(?<=]).".toRegex()
}

class netCloudMusic{
    val songCover = "(?<=\"images\": \\[\").*?(?<=]).".toRegex()
}