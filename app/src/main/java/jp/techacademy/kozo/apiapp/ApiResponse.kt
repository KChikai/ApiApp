package jp.techacademy.kozo.apiapp

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ApiResponse(
    @SerializedName("results")
    val results: Results
)

data class Results(
    @SerializedName("shop")
    var shop: List<Shop>
)

data class Shop(
    @SerializedName("id")
    var id: String,
    @SerializedName("coupon_urls")
    var couponUrls: CouponUrls,
    @SerializedName("logo_image")
    var logoImage: String,
    @SerializedName("name")
    var name: String
): Serializable

data class CouponUrls(
    @SerializedName("pc")
    var pc: String,
    @SerializedName("sp")
    var sp: String
): Serializable