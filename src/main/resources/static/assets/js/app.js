$.getScript("/assets/js/js/plugin.js", function() {
    $(function() {
        REQUEST_PATH = "/auth/"
        REQUEST_PATH_API = "/api/client/"
        REQUEST_PATH_API_ADMIN = "/api/admin/"

        $(document).on('submit', '#register', function(e) {
            e.preventDefault();
            var data = formToJson($("#register"));
            ajaxRequest(data, 'POST', REQUEST_PATH + 'register', response)
        });
        
        $(document).on('submit', '#login', function(e) {
            e.preventDefault();
            var data = formToJson($("#login"));
            ajaxRequest(data, 'POST', REQUEST_PATH + 'login', responsewithData)
        });

        $(document).on("click", ".closeNotification", function(e) {
            e.preventDefault()
            id = $(this).data("id")
            ajaxRequest(null, "DELETE", REQUEST_PATH_API + "notification/" + id, responsewithData)
        });

        $(document).on('submit', '#profileChange', function(e) {
            e.preventDefault();
            var image = $('#image').prop('files')[0];
            var data = new FormData($("#profileChange")[0]);
            data.append('image', image);
            ajaxUploadRequest(data, 'POST', REQUEST_PATH_API + 'profil', response)
        });


        $(document).on("click", ".activeAccount", function(e) {
            e.preventDefault();
            $this = $(this)
            link = REQUEST_PATH_API_ADMIN + 'client/pending/' + $($this).data("id");
            swalAlert("ATTENTION ACTIVATION ?", "DO YOU REALLY WANT TO ACTIVATE THIS ACCOUNT ? ", $(this), null, "PATCH", link, responseSwal)

        });

        $(document).on("click", ".deleteAccount", function(e) {
            e.preventDefault();
            $this = $(this)
            link = REQUEST_PATH_API_ADMIN + 'client/pending/' + $($this).data("id");
            swalAlert("ATTENTION DELETED ?", "DO YOU REALLY WANT TO DELETE THIS ACCOUNT ? ", $(this), null, "DELETE", link, responsewithData)
        })

        $(document).on("click", '.detailAccount', function(e) {
            e.preventDefault();
            $this = $(this)
            link = REQUEST_PATH_API_ADMIN + 'client/pending/' + $($this).data("id");
            ajaxRequest(null, "GET", link, function(response) {
                user_info = response.user_info[0]
                user = response.user
                $('#name').val(user_info.name)
                $('#firstname').val(user_info.firstname)
                $('#country').val(user_info.country)
                $('#city').val(user_info.city)
                $('#address').val(user_info.address)
                $('#phone').val(user_info.phone)
                $('#postalcode').val(user_info.postalcode)
                $('#birthday').val(user_info.birthday)
                $('#email').val(user.email)
                $("#passwordD").val(user_info.password)
                $('#user-form-modal').modal("show")
            })
        })

        $(document).on("click", ".d", function(e) {
            e.preventDefault()
            $this = $(this)
            id = $($this).data("id");
            $("#d").modal("show")
            $(document).on("submit", "#dForm", function(e) {
                e.preventDefault()
                var data = formToJson($("#dForm"));
                ajaxRequest(data, "POST", REQUEST_PATH_API_ADMIN + "bank/debit/" + id, response)
            })
        })
        $(document).on("click", ".transfertC", function(e) {
            e.preventDefault()
            $this = $(this)
            id = $($this).data("id");
            $("#transfertM").modal("show")
            $(document).on("submit", "#transfertCForm", function(e) {
                e.preventDefault()
                var data = formToJson($("#transfertCForm"));
                ajaxRequest(data, "POST", REQUEST_PATH_API_ADMIN + "bank/transfer/" + id, response)
            })
        })
        $(document).on("click", ".c", function(e) {
            e.preventDefault()
            $this = $(this)
            id = $($this).data("id");
            $("#c").modal("show")
            $(document).on("submit", "#cForm", function(e) {
                e.preventDefault()
                var data = formToJson($("#cForm"));
                ajaxRequest(data, "POST", REQUEST_PATH_API_ADMIN + "bank/credit/" + id, response)
            })
        })
        $(document).on("click", ".dA", function(e) {
            e.preventDefault()
            $this = $(this)
            id = $($this).data("id");
            swalAlert(
                "ATTENTION DELETED ?", "DO YOU REALLY WANT TO DELETE THIS ACCOUNT ? ",
                $(this), null, "DELETE", REQUEST_PATH_API_ADMIN + 'client/pending/' + id, responseSwal
            )
        })

       

        $(document).on("click",".deleteTransfer",function(e){
            e.preventDefault()
            $this = $(this)
            id = $($this).data("id");
            link = REQUEST_PATH_API + 'transfert/' + id;
            swalAlert(
                "ATTENTION DELETED ?", "DO YOU REALLY WANT TO DELETE THIS ACCOUNT ? ",
                $(this), null, "DELETE", link, responseSwal
            )
        })
        $(document).on("submit", ".cb-form", function(e) {
            e.preventDefault()
            var data = formToJson($(".cb-form"));
            ajaxRequest(data, "POST", "/api/contact", response)
        })
    });
})
