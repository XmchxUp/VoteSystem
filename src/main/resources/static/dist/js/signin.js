$(function () {
    $("#btnLogin").click(function (event) {

        //Fetch form to apply custom Bootstrap validation
        var form = $("#formLogin");

        if (form[0].checkValidity() === false) {
            event.preventDefault();
            event.stopPropagation();
            form.addClass('was-validated');
        }
    });


});
