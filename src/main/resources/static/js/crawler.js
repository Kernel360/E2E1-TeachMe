const fastcampus_btn = document.getElementById("fastcampus_crawler_button");
const inflearn_btn = document.getElementById("inflearn_crawler_button");

fastcampus_btn.addEventListener("click", crawlFastcampusData);
inflearn_btn.addEventListener("click", crawlInflearnData);

function crawlFastcampusData() {
    fastcampus_btn.innerText = "크롤링 중..";
    fastcampus_btn.disabled = true;

    const inputData = {"platform" : "fastcampus"};

    $.ajax({
        url: "/crawler/crawling",
        type: "post",
        data: JSON.stringify(inputData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function(data) {
            alert(data);
            fastcampus_btn.innerText = "크롤링 완료!";
            fastcampus_btn.disabled = false
            fastcampus_btn.removeEventListener("click", crawlFastcampusData);
        }
    });
}

function crawlInflearnData() {
    inflearn_btn.innerText = "크롤링 중..";
    inflearn_btn.disabled = true;

    const inputData = {"platform" : "inflearn"};

    $.ajax({
        url: "/crawler/crawling",
        type: "post",
        data: JSON.stringify(inputData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        async: false,
        success: function(data) {
            alert(data);
            inflearn_btn.innerText = "크롤링 완료!";
            inflearn_btn.disabled = false
            inflearn_btn.removeEventListener("click", crawlInflearnData);
        }
    })
}