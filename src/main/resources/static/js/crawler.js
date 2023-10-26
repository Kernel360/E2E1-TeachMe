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
        success: function(data) {
            if(data.message === "크롤링 성공") {
                fastcampus_btn.innerText = "크롤링 완료!";
            } else {
                fastcampus_btn.innerText = "크롤링 실패";
            }
            fastcampus_btn.disabled = false;
            fastcampus_btn.removeEventListener("click", crawlFastcampusData);
        },
        error: function(e) {
            alert(e);
            console.log(e);
            fastcampus_btn.innerText = "크롤링 실패";
            fastcampus_btn.disabled = false;
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
        success: function(data) {
            if(data.message === "크롤링 성공") {
                inflearn_btn.innerText = "크롤링 완료!";
            } else {
                inflearn_btn.innerText = "크롤링 실패";
            }
            inflearn_btn.disabled = false;
            inflearn_btn.removeEventListener("click", crawlInflearnData);
        },
        error: function() {
            alert("error");
            inflearn_btn.innerText = "크롤링 실패";
            inflearn_btn.disabled = false;
        }
    })
}