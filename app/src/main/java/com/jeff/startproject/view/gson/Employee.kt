package com.jeff.startproject.view.gson


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Employee(
  val my: String?,

  @Transient // 不序列化
  @SerializedName("address")
  val address: Address,

  @SerializedName("cities")
  val cities: List<String>,

  @SerializedName("empID")
  val empID: Int,

  @Expose
  @SerializedName("name")
  val name: String,

  @SerializedName("permanent")
  val permanent: Boolean,

  @SerializedName("phoneNumbers")
  val phoneNumbers: List<Int>,

  @SerializedName("properties")
  val properties: Properties,

  @SerializedName("role")
  val role: String
) {
    data class Address(
      @SerializedName("city")
      val city: String,

      @SerializedName("street")
      val street: String,

      @SerializedName("zipcode")
      val zipcode: Int
    )

    data class Properties(
      @SerializedName("age")
      val age: String,

      @SerializedName("salary")
      val salary: String
    )
}