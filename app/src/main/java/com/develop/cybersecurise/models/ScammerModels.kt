package com.develop.cybersecurise.models

data class ScammerModels(
    val severity: String? = null,
    val data: List<ScammerData?>? = null,
    val page:Int? = null,
    val status:Int? = null,
    val total:Int? = null,
)

data class ScammerData(
    val bank_to_abbr: String? = null,
    val bank_to_acct: String? = null,
    val createdTime: String? = null,
    val dataSource: String? = null,
    val status: String? = null,
)