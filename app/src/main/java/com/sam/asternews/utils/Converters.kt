package com.sam.asternews.utils

import org.jsoup.Jsoup

fun description(html: String): String {
    return Jsoup.parse(html).text()
}