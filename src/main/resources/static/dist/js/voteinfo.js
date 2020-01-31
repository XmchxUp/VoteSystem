var expirationDateTime;
$(function () {
    var pollId = $("#pollId").val();


    const pieSvg = document.querySelector('.pie-chart');
    const barSvg = document.querySelector('.bar-chart');

    let d = $("#expirationDateTime").text();
    expirationDateTime = new Date(d);

    setInterval(setTime, 1000);

    setInterval(function () {
        $.getJSON("/api/polls/" + pollId).done(function (data) {

            const barChart = new chartXkcd.Bar(barSvg, {
                title: '数量图', // optional
                data: {
                    labels: data.labels,
                    datasets: [{
                        data: data.values,
                    }],
                },
                options: { // optional
                    yTickCount: 2,
                },
            });

            // 转换成百分比
            var sum = 0;
            var len = getJsonLength(data.values);
            for (let i = 0; i < len; i++) {
                sum += data.values[i];
            }
            var newValues = [];
            for (let i = 0; i < len; i++) {
                newValues[i] = ((data.values[i] / sum) * 100).toFixed(2);
            }
            const pieChart = new chartXkcd.Pie(pieSvg, {
                title: '百分比图', // optional
                data: {
                    labels: data.labels,
                    datasets: [{
                        data: newValues,
                    }],
                },
                options: { // optional
                    innerRadius: 0.5,
                    legendPosition: chartXkcd.config.positionType.upRight,
                },
            });

        });
    }, 2000);
});

/**设置倒计时*/
function setTime() {
    var now = new Date();
    var now_time = new Date().getTime();
    var deadline_time = new Date(expirationDateTime).getTime();
    // 设置时间样式 天 小时 分 秒
    var time_diff = deadline_time - now_time;
    // 天
    var days = Math.floor(time_diff / (24 * 3600 * 1000));
    // 小时
    var hours = Math.floor(time_diff / (3600 * 1000));
    // 分
    var minutes = Math.floor(time_diff / (60 * 1000));
    // 秒
    var seconds = Math.floor(time_diff / 1000);
    $("#dead_day").text(days+'天');
    $("#dead_hour").text(hours - days * 24+'时');
    $("#dead_minute").text(minutes - hours * 60+'分');
    $("#dead_second").text(seconds - minutes * 60+'秒');
}


/**
 * 获取Json数据的长度
 * */
function getJsonLength(jsonData) {
    var jsonLength = 0;
    for (var item in jsonData) {
        jsonLength++;
    }
    return jsonLength;
}