$(function () {
    var pollId = $("#pollId").val();


    const pieSvg = document.querySelector('.pie-chart');
    const barSvg = document.querySelector('.bar-chart');


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