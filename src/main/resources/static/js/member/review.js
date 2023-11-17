function showEditForm(reviewId) {
    const reviewBox = document.createElement("div");
    reviewBox.className = "rating_box";
    reviewBox.innerHTML = "<div class=\"rating\">\n" +
        "                        ★★★★★\n" +
        "                        <span class=\"rating_star\">★★★★★</span>\n" +
        "                        <input id=\"review_score\" type=\"range\" value=\"0\" step=\"1\" min=\"0\" max=\"10\">\n" +
        "                    </div>"

    const reviewLabel = document.createElement("label");
    reviewLabel.style.width = "100%";
    reviewLabel.style.marginBottom = "30px";
    reviewLabel.style.display = "flex";

    reviewLabel.innerHTML = "<textarea id=\"review_text\" placeholder=\"당신의 리뷰를 남겨주세요 ✏️\" class=\"review_area\"></textarea>\n" +
        "                    <button id=\"review_button\" class=\"review_button\">\n" +
        "                        <i class=\"fa-solid fa-pen-to-square\"></i>\n" +
        "                    </button>"

    const currentReviewDiv = document.querySelector(".box" + reviewId);
    currentReviewDiv.parentNode.insertBefore(reviewBox, currentReviewDiv.nextSibling);
    reviewBox.parentNode.insertBefore(reviewLabel, reviewBox.nextSibling);

    currentReviewDiv.style.display = "none";

    const rating_input = document.querySelector('.rating input');
    const rating_star = document.querySelector('.rating_star');

    rating_input.addEventListener('change', () => {
        rating_star.style.width = `${rating_input.value * 10}%`;
    });
}

function remove(reviewId) {

    const removeData = {
        "reviewId" : reviewId
    }

    $.ajax({
        url:"/review/delete",
        type:"delete",
        data: JSON.stringify(removeData),
        dataType: "json",
        contentType: "application/json;charset=UTF-8",
        success: function() {
            location.reload(true);
        },
        error: function(e) {
            alert("리뷰 삭제 중 오류가 발생하였습니다");
            console.log(e);
        }
    })
}

