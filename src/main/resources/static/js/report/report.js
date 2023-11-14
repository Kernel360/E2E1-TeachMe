window.onload = function() {
    setDefaultDate();
    loadTotalChartData();
    loadAverageScoreChart();
    loadReviewCountChart();
}

const startDate = document.getElementById("startDate");
const endDate = document.getElementById("endDate");
const averScoreDate = document.getElementById("aver-score-date");
const reviewCountDate = document.getElementById("review-count-date");

startDate.addEventListener("change", loadTotalChartData);
endDate.addEventListener("change", loadTotalChartData);
averScoreDate.addEventListener("change", loadAverageScoreChart);
reviewCountDate.addEventListener("change", loadReviewCountChart);

function setDefaultDate() {
    const today = new Date();
    const sevenDaysAgo = new Date(today);
    today.setDate(today.getDate() - 1);
    sevenDaysAgo.setDate(today.getDate() - 7);

    const formattedToday = formatDate(today);
    const formattedSevenDaysAgo = formatDate(sevenDaysAgo);

    document.getElementById("startDate").value = formattedSevenDaysAgo;
    document.getElementById("endDate").value = formattedToday;
    document.getElementById("aver-score-date").value = formattedToday;
    document.getElementById("review-count-date").value = formattedToday;
}

function formatDate(date) {
    let d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2)
        month = '0' + month;
    if (day.length < 2)
        day = '0' + day;

    return [year, month, day].join('-');
}

function loadTotalChartData() {
    const startDateVal = document.getElementById("startDate").value;
    const endDateVal = document.getElementById("endDate").value;

    const today = new Date();
    if(today <= new Date(endDateVal)) {
        alert("오늘 이후의 값을 설정할 수 없습니다");
        endDate.value = "";
        return;
    }

    if(new Date(startDateVal) > new Date(endDateVal)) {
        alert("시작일은 종료일 보다 이전 날짜여야 합니다");
        startDate.value = "";
        return;
    }

    fetch(`/api/report/statistics?startDate=${startDateVal}&endDate=${endDateVal}`)
        .then(response => response.json())
        .then(data => {
            const labels = Object.keys(data).sort();
            const values = labels.map(label => data[label].reviewCount);

            const ctx = document.getElementById("reviewChart").getContext("2d");
            new Chart(ctx, {
                type: "line",
                data: {
                    labels: labels,
                    datasets: [{
                        label: "리뷰 수",
                        data: values,
                        backgroundColor: 'rgba(0, 123, 255, 0.5)',
                        borderColor: 'rgba(0, 123, 255, 1)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        });
}

function loadAverageScoreChart() {
    const date = document.getElementById("aver-score-date").value;

    const today = new Date();
    if(today <= new Date(date)) {
        alert("오늘 이후의 값을 설정할 수 없습니다");
        averScoreDate.value = "";
        return;
    }

    fetch(`/api/report/average?date=${date}`)
        .then(response => response.json())
        .then(data => {
            const maxLength = 10;
            const labels = Object.keys(data).map(label => {
                if (label.length > maxLength) {
                    return label.substring(0, maxLength) + "...";
                }
                return label;
            });
            const values = Object.values(data);

            const ctx = document.getElementById('averageScoreChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '강의별 평균 평점',
                        data: values,
                        backgroundColor: 'rgba(255, 99, 132, 0.5)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        });
}

function loadReviewCountChart() {
    const date = document.getElementById("review-count-date").value;

    const today = new Date();
    if(today <= new Date(date)) {
        alert("오늘 이후의 값을 설정할 수 없습니다");
        reviewCountDate.value = "";
        return;
    }

    fetch(`/api/report/review-count?date=${date}`)
        .then(response => response.json())
        .then(data => {
            const maxLength = 10;
            const labels = Object.keys(data).map(label => {
                if (label.length > maxLength) {
                    return label.substring(0, maxLength) + "...";
                }
                return label;
            });
            const values = Object.values(data);

            const ctx = document.getElementById('reviewCountChart').getContext('2d');
            new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: '강의별 리뷰 개수',
                        data: values,
                        backgroundColor: 'rgba(54, 162, 132, 0.5)',
                        borderWidth: 1
                    }]
                },
                options: {
                    scales: {
                        y: {
                            beginAtZero: true
                        }
                    }
                }
            });
        });
}