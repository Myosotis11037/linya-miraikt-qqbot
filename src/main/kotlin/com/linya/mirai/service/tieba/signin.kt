package com.linya.mirai.service.tieba

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.request.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.mamoe.mirai.contact.Group
import net.mamoe.mirai.contact.Member
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeGroupMessages
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.message.data.buildMessageChain


var cookie = "BIDUPSID=9D96E01732C84E3EF46E6D69F715EB8E; PSTM=1574597643; bdshare_firstime=1574667391465; rpln_guide=1; H_WISE_SIDS=147935_162057_156287_159609_162914_155225_161299_163303_161266_162371_159382_159937_161421_157263_161419_161970_127969_161770_160102_161958_160897_161729_162347_131423_160861_128698_161082_153149_162445_158055_160800_162169_161965_159954_160422_162474_162151_144966_162095_162187_161239_139883_158640_155530_163114_147552_162479_162267_162524_162861_162816_162642_159092_162264_162261_162155_110085_162026_163321; BAIDUID=CA1D410F7713287242D266621C18831C:FG=1; __yjs_duid=1_2f71f9689f273d49d3b607ed4bead1ca1611406958065; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; H_PS_PSSID=33423_33582_33273_31253_26350_33544; delPer=0; PSINO=7; BAIDUID_BFESS=308E2AF32F2705030DB38E99B12C6328:FG=1; BDRCVFR[feWj1Vr5u3D]=mk3SLVN4HKm; BA_HECTOR=2kal01a58ga42h8gqt1g27n8q0r; st_key_id=17; Hm_lvt_98b9d8c2fd6608d564bf2ac2ae642948=1612899973,1612899985,1612963100,1612963106; ab_sr=1.0.0_OTNlZDA4ZTNjNWQzYzEyZTg3NmU3ZTU2ZTM0OTYzMzM2NWFhOTgwMThmNWU4N2Y5YWExNWExOTM2ZThmM2JmMTJlOTZmZTRhYzE2ODZiOGJjMTQ4MjEyNTJkZjY1OTZlODZiZjg2NDE4MWRiZDJmZmUxNWRmN2JiZTgzM2ZmZTA=; st_data=6ff647c25e22e6e2098ddd2b4d912445ecd2b7a96a113d85893a95c7106afea705096a5203902ba371dce271f377c6fe1cf78cee29958d81bc1b2eefaafff0eb919f7810870e1562e9e0da7fd55f383a36176d3d772d68e90ff7eb8e121e5085d76aa9b6314c23eebd55995d0777b5950d21b55485d174f84dafb08ea9375a31; st_sign=8f3d7169; baidu_broswer_setup_sargarse=0; Hm_lpvt_98b9d8c2fd6608d564bf2ac2ae642948=1612963136; BDUSS=0lQdzl0LUtwRGdvSmJILTVuaDRsRjJndG9VV25rMVFnVDA5M0JjV0JKaG9ha3RnRVFBQUFBJCQAAAAAAAAAAAEAAACuIkFKc2FyZ2Fyc2UAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGjdI2Bo3SNgZ; BDUSS_BFESS=0lQdzl0LUtwRGdvSmJILTVuaDRsRjJndG9VV25rMVFnVDA5M0JjV0JKaG9ha3RnRVFBQUFBJCQAAAAAAAAAAAEAAACuIkFKc2FyZ2Fyc2UAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGjdI2Bo3SNgZ; STOKEN=1475d1ef2d029f121173478668e6605d6dd6dbc639869b78c0e1318306d5f9af"
val format = Json { ignoreUnknownKeys = true }

fun tiebaSignEntrance(){
    GlobalEventChannel.subscribeGroupMessages() {
        case("贴吧签到") {
            if(sender.id == 5980403.toLong()) tiebaSignIn(sender,group)
            else group.sendMessage("你没有使用该功能的权限哦~")
        }
    }
}

suspend fun tiebaSignIn(sender: Member, group : Group){
    val tiebaSignUrl = "https://tieba.baidu.com/tbmall/onekeySignin1"
    val client = HttpClient(OkHttp)
    val resp = client.post<String>{
        url(tiebaSignUrl)
        header("cookie",cookie)
        parameter("ie","utf-8")
        parameter("tbs","dbcb633d0a5796b81612963177")
    }
    val tiebaSign = format.decodeFromString<tiebaSignJson>(resp)
    val signedForumAmount = tiebaSign.data.signedForumAmount
    val unsignedForumAmount = tiebaSign.data.unsignedForumAmount
    val message = buildMessageChain {
        +At(sender.id)
    }
    val signReport = "签到成功的贴吧为" +
        signedForumAmount +
        "个\n" +
        "签到失败的贴吧为" +
        unsignedForumAmount + "个"
    group.sendMessage(message.plus(signReport))
}