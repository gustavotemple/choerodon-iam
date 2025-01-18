var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "27844",
        "ok": "27649",
        "ko": "195"
    },
    "minResponseTime": {
        "total": "6",
        "ok": "6",
        "ko": "21"
    },
    "maxResponseTime": {
        "total": "26999",
        "ok": "26999",
        "ko": "12107"
    },
    "meanResponseTime": {
        "total": "259",
        "ok": "244",
        "ko": "2379"
    },
    "standardDeviation": {
        "total": "1262",
        "ok": "1237",
        "ko": "2415"
    },
    "percentiles1": {
        "total": "51",
        "ok": "50",
        "ko": "1200"
    },
    "percentiles2": {
        "total": "146",
        "ok": "143",
        "ko": "4462"
    },
    "percentiles3": {
        "total": "593",
        "ok": "531",
        "ko": "5573"
    },
    "percentiles4": {
        "total": "3975",
        "ok": "3378",
        "ko": "10326"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 26582,
    "percentage": 95
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 234,
    "percentage": 1
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 833,
    "percentage": 3
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 195,
    "percentage": 1
},
    "meanNumberOfRequestsPerSecond": {
        "total": "45.201",
        "ok": "44.885",
        "ko": "0.317"
    }
},
contents: {
"group_project-1355342585": {
          type: "GROUP",
name: "Project",
path: "Project",
pathFormatted: "group_project-1355342585",
stats: {
    "name": "Project",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2642",
        "ko": "178"
    },
    "minResponseTime": {
        "total": "156",
        "ok": "156",
        "ko": "508"
    },
    "maxResponseTime": {
        "total": "13390",
        "ok": "9675",
        "ko": "13390"
    },
    "meanResponseTime": {
        "total": "1116",
        "ok": "782",
        "ko": "6065"
    },
    "standardDeviation": {
        "total": "1901",
        "ok": "1134",
        "ko": "3464"
    },
    "percentiles1": {
        "total": "514",
        "ok": "471",
        "ko": "6251"
    },
    "percentiles2": {
        "total": "896",
        "ok": "820",
        "ko": "9072"
    },
    "percentiles3": {
        "total": "5673",
        "ok": "2515",
        "ko": "11617"
    },
    "percentiles4": {
        "total": "9971",
        "ok": "6550",
        "ko": "12776"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 2642,
    "percentage": 94
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 178,
    "percentage": 6
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.289",
        "ko": "0.289"
    }
},
contents: {
"req_post-create-pro--1209502008": {
        type: "REQUEST",
        name: "POST create-project",
path: "Project / POST create-project",
pathFormatted: "req_project---post---884925584",
stats: {
    "name": "POST create-project",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2642",
        "ko": "178"
    },
    "minResponseTime": {
        "total": "51",
        "ok": "51",
        "ko": "177"
    },
    "maxResponseTime": {
        "total": "5747",
        "ok": "1190",
        "ko": "5747"
    },
    "meanResponseTime": {
        "total": "245",
        "ok": "111",
        "ko": "2243"
    },
    "standardDeviation": {
        "total": "736",
        "ok": "98",
        "ko": "2043"
    },
    "percentiles1": {
        "total": "86",
        "ok": "84",
        "ko": "1376"
    },
    "percentiles2": {
        "total": "118",
        "ok": "108",
        "ko": "4429"
    },
    "percentiles3": {
        "total": "563",
        "ok": "241",
        "ko": "5387"
    },
    "percentiles4": {
        "total": "5055",
        "ok": "587",
        "ko": "5710"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2629,
    "percentage": 93
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 13,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 178,
    "percentage": 6
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.289",
        "ko": "0.289"
    }
}
    },"req_get-list-all-pr-1600511864": {
        type: "REQUEST",
        name: "GET list-all-projects (mem hotspot)",
path: "Project / GET list-all-projects (mem hotspot)",
pathFormatted: "req_project---get-l--2073021408",
stats: {
    "name": "GET list-all-projects (mem hotspot)",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2820",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "11",
        "ok": "11",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "5382",
        "ok": "5382",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "379",
        "ok": "379",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "654",
        "ok": "654",
        "ko": "-"
    },
    "percentiles1": {
        "total": "169",
        "ok": "169",
        "ko": "-"
    },
    "percentiles2": {
        "total": "328",
        "ok": "328",
        "ko": "-"
    },
    "percentiles3": {
        "total": "1958",
        "ok": "1958",
        "ko": "-"
    },
    "percentiles4": {
        "total": "3210",
        "ok": "3210",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2525,
    "percentage": 90
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 52,
    "percentage": 2
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 243,
    "percentage": 9
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.578",
        "ko": "-"
    }
}
    },"req_get-list-enable-1448648536": {
        type: "REQUEST",
        name: "GET list-enabled-projects (mem hotspot)",
path: "Project / GET list-enabled-projects (mem hotspot)",
pathFormatted: "req_project---get-l--1890033664",
stats: {
    "name": "GET list-enabled-projects (mem hotspot)",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2820",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "7",
        "ok": "7",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "4799",
        "ok": "4799",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "374",
        "ok": "374",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "654",
        "ok": "654",
        "ko": "-"
    },
    "percentiles1": {
        "total": "160",
        "ok": "160",
        "ko": "-"
    },
    "percentiles2": {
        "total": "324",
        "ok": "324",
        "ko": "-"
    },
    "percentiles3": {
        "total": "1935",
        "ok": "1935",
        "ko": "-"
    },
    "percentiles4": {
        "total": "3264",
        "ok": "3264",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2516,
    "percentage": 89
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 57,
    "percentage": 2
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 247,
    "percentage": 9
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.578",
        "ko": "-"
    }
}
    },"req_put-disable-pro--1104339453": {
        type: "REQUEST",
        name: "PUT disable-project",
path: "Project / PUT disable-project",
pathFormatted: "req_project---put-d--779763029",
stats: {
    "name": "PUT disable-project",
    "numberOfRequests": {
        "total": "2642",
        "ok": "2642",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "30",
        "ok": "30",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "2125",
        "ok": "2125",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "65",
        "ok": "65",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "103",
        "ok": "103",
        "ko": "-"
    },
    "percentiles1": {
        "total": "44",
        "ok": "44",
        "ko": "-"
    },
    "percentiles2": {
        "total": "55",
        "ok": "55",
        "ko": "-"
    },
    "percentiles3": {
        "total": "132",
        "ok": "132",
        "ko": "-"
    },
    "percentiles4": {
        "total": "531",
        "ok": "531",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2628,
    "percentage": 99
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 11,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 3,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.289",
        "ok": "4.289",
        "ko": "-"
    }
}
    },"req_put-enable-proj--1240361440": {
        type: "REQUEST",
        name: "PUT enable-project",
path: "Project / PUT enable-project",
pathFormatted: "req_project---put-e--398607240",
stats: {
    "name": "PUT enable-project",
    "numberOfRequests": {
        "total": "2642",
        "ok": "2642",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "28",
        "ok": "28",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "991",
        "ok": "991",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "60",
        "ok": "60",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "80",
        "ok": "80",
        "ko": "-"
    },
    "percentiles1": {
        "total": "42",
        "ok": "42",
        "ko": "-"
    },
    "percentiles2": {
        "total": "53",
        "ok": "53",
        "ko": "-"
    },
    "percentiles3": {
        "total": "122",
        "ok": "122",
        "ko": "-"
    },
    "percentiles4": {
        "total": "481",
        "ok": "481",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2634,
    "percentage": 100
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 8,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.289",
        "ok": "4.289",
        "ko": "-"
    }
}
    }
}

     },"group_user-2645995": {
          type: "GROUP",
name: "User",
path: "User",
pathFormatted: "group_user-2645995",
stats: {
    "name": "User",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2813",
        "ko": "7"
    },
    "minResponseTime": {
        "total": "198",
        "ok": "198",
        "ko": "11173"
    },
    "maxResponseTime": {
        "total": "30586",
        "ok": "30586",
        "ko": "25818"
    },
    "meanResponseTime": {
        "total": "1444",
        "ok": "1408",
        "ko": "16068"
    },
    "standardDeviation": {
        "total": "4290",
        "ok": "4222",
        "ko": "6049"
    },
    "percentiles1": {
        "total": "323",
        "ok": "323",
        "ko": "12514"
    },
    "percentiles2": {
        "total": "447",
        "ok": "446",
        "ko": "19556"
    },
    "percentiles3": {
        "total": "10420",
        "ok": "9705",
        "ko": "25658"
    },
    "percentiles4": {
        "total": "24255",
        "ok": "23879",
        "ko": "25786"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 0,
    "percentage": 0
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 2813,
    "percentage": 100
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 7,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.567",
        "ko": "0.011"
    }
},
contents: {
"req_post-create-use--1191764938": {
        type: "REQUEST",
        name: "POST create-user (cpu hotspot)",
path: "User / POST create-user (cpu hotspot)",
pathFormatted: "req_user---post-cre--1666455588",
stats: {
    "name": "POST create-user (cpu hotspot)",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2815",
        "ko": "5"
    },
    "minResponseTime": {
        "total": "127",
        "ok": "127",
        "ko": "9917"
    },
    "maxResponseTime": {
        "total": "26999",
        "ok": "26999",
        "ko": "12107"
    },
    "meanResponseTime": {
        "total": "1112",
        "ok": "1095",
        "ko": "10700"
    },
    "standardDeviation": {
        "total": "3583",
        "ok": "3563",
        "ko": "832"
    },
    "percentiles1": {
        "total": "201",
        "ok": "201",
        "ko": "10271"
    },
    "percentiles2": {
        "total": "267",
        "ok": "266",
        "ko": "11181"
    },
    "percentiles3": {
        "total": "7884",
        "ok": "7252",
        "ko": "11922"
    },
    "percentiles4": {
        "total": "20264",
        "ok": "20269",
        "ko": "12070"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2554,
    "percentage": 91
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 35,
    "percentage": 1
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 226,
    "percentage": 8
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 5,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.57",
        "ko": "0.008"
    }
}
    },"req_get-list-users--441701565": {
        type: "REQUEST",
        name: "GET list-users",
path: "User / GET list-users",
pathFormatted: "req_user---get-list--1983315223",
stats: {
    "name": "GET list-users",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2820",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "6",
        "ok": "6",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "1058",
        "ok": "1058",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "54",
        "ok": "54",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "110",
        "ok": "110",
        "ko": "-"
    },
    "percentiles1": {
        "total": "28",
        "ok": "28",
        "ko": "-"
    },
    "percentiles2": {
        "total": "46",
        "ok": "46",
        "ko": "-"
    },
    "percentiles3": {
        "total": "175",
        "ok": "175",
        "ko": "-"
    },
    "percentiles4": {
        "total": "699",
        "ok": "699",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2803,
    "percentage": 99
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 17,
    "percentage": 1
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 0,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.578",
        "ko": "-"
    }
}
    },"req_get-one-user-1764391036": {
        type: "REQUEST",
        name: "GET one-user",
path: "User / GET one-user",
pathFormatted: "req_user---get-one---909835870",
stats: {
    "name": "GET one-user",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2820",
        "ko": "0"
    },
    "minResponseTime": {
        "total": "8",
        "ok": "8",
        "ko": "-"
    },
    "maxResponseTime": {
        "total": "1202",
        "ok": "1202",
        "ko": "-"
    },
    "meanResponseTime": {
        "total": "38",
        "ok": "38",
        "ko": "-"
    },
    "standardDeviation": {
        "total": "97",
        "ok": "97",
        "ko": "-"
    },
    "percentiles1": {
        "total": "16",
        "ok": "16",
        "ko": "-"
    },
    "percentiles2": {
        "total": "21",
        "ok": "21",
        "ko": "-"
    },
    "percentiles3": {
        "total": "127",
        "ok": "127",
        "ko": "-"
    },
    "percentiles4": {
        "total": "573",
        "ok": "573",
        "ko": "-"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2805,
    "percentage": 99
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 14,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 1,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 0,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.578",
        "ko": "-"
    }
}
    },"req_put-disable-use-51581217": {
        type: "REQUEST",
        name: "PUT disable-user",
path: "User / PUT disable-user",
pathFormatted: "req_user---put-disa-324572999",
stats: {
    "name": "PUT disable-user",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2813",
        "ko": "7"
    },
    "minResponseTime": {
        "total": "25",
        "ok": "25",
        "ko": "592"
    },
    "maxResponseTime": {
        "total": "12462",
        "ok": "12462",
        "ko": "3421"
    },
    "meanResponseTime": {
        "total": "177",
        "ok": "174",
        "ko": "1532"
    },
    "standardDeviation": {
        "total": "687",
        "ok": "681",
        "ko": "1185"
    },
    "percentiles1": {
        "total": "38",
        "ok": "38",
        "ko": "1019"
    },
    "percentiles2": {
        "total": "52",
        "ok": "52",
        "ko": "2191"
    },
    "percentiles3": {
        "total": "580",
        "ok": "542",
        "ko": "3401"
    },
    "percentiles4": {
        "total": "3885",
        "ok": "3889",
        "ko": "3417"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2690,
    "percentage": 95
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 20,
    "percentage": 1
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 103,
    "percentage": 4
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 7,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.567",
        "ko": "0.011"
    }
}
    },"req_put-enable-user--193079772": {
        type: "REQUEST",
        name: "PUT enable-user",
path: "User / PUT enable-user",
pathFormatted: "req_user---put-enab--738462914",
stats: {
    "name": "PUT enable-user",
    "numberOfRequests": {
        "total": "2820",
        "ok": "2815",
        "ko": "5"
    },
    "minResponseTime": {
        "total": "21",
        "ok": "25",
        "ko": "21"
    },
    "maxResponseTime": {
        "total": "1492",
        "ok": "1492",
        "ko": "338"
    },
    "meanResponseTime": {
        "total": "64",
        "ok": "64",
        "ko": "91"
    },
    "standardDeviation": {
        "total": "112",
        "ok": "112",
        "ko": "124"
    },
    "percentiles1": {
        "total": "39",
        "ok": "39",
        "ko": "29"
    },
    "percentiles2": {
        "total": "51",
        "ok": "51",
        "ko": "41"
    },
    "percentiles3": {
        "total": "179",
        "ok": "179",
        "ko": "279"
    },
    "percentiles4": {
        "total": "529",
        "ok": "530",
        "ko": "326"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 2798,
    "percentage": 99
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 7,
    "percentage": 0
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 10,
    "percentage": 0
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 5,
    "percentage": 0
},
    "meanNumberOfRequestsPerSecond": {
        "total": "4.578",
        "ok": "4.57",
        "ko": "0.008"
    }
}
    }
}

     }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}