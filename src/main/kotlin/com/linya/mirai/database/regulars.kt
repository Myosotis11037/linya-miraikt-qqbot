package com.linya.mirai.database

class Bilibili{
    //��Ƶ���ʽ
    val bvFind = "BV1[1-9A-NP-Za-km-z]{9}".toRegex()
    val avFind = "(av|AV)[1-9]\\d{0,18}".toRegex()

    //b23������
    val b23Find = "(https?://b23.tv/\\S{6})".toRegex()

    //��ʽ���������س�
    val deleteEnter = "(\\cJ\\cJ)+".toRegex()

    //������Ƶ�ܰ�
    val touhouFormat = "(?<=\"list\":\\[).*?(?<=]).".toRegex()
}

class netCloudMusic{
    val songCover = "(?<=\"images\": \\[\").*?(?<=]).".toRegex()
}