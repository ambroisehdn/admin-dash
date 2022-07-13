$.getScript("/assets/js/plugin.js", function () {

    $(document).ready(function () {
        REQUEST_PATH = "/api/user/"

        var userDataTable = $('#userTable').DataTable({
            "sAjaxSource": "/api/user",
            "sAjaxDataProp": "",
            "order": [
                [0, "asc"]
            ],
            "aoColumns": [{
                    "mData": "id"
                },
                {
                    "mData": "username"
                },
                {
                    "mData": "role.rolename"
                },
                {
                    "render": function (data, type, row) {
                        return '<div class="text-center"> <a href="#' + row.id + '" data-id="' + row.id + '" class="btn btn-info btn-sm ml-2 editUser"> <span> <i class="la la-edit"></i></span></a>  <a data-id="' + row.id + '" href="#' + row.id + '" class="btn btn-danger btn-sm ml-2 deleteUser"> <span> <i class="la la-trash"></i> </span> </a>   </div>';
                    }
                },
            ]
        })

        $(document).on("click", "#addUser", function (e) {
            e.preventDefault()
            $('#userModal').modal('show')
        });


        $(document).on('submit', '#userForm', function (e) {
            e.preventDefault();
            var form_data = $(this).serialize();
            if (form_data.indexOf('=&') > -1 || form_data.substr(form_data.length - 1) == '=') {
                swal("Error", "Veuillez remplir tous les champs", "error");
            } else {
                var data = {
                    "username": $('#username').val(),
                    "password": $("#password").val(),
                    "role": {
                        "id": $('#role_id').val(),
                    }
                }
                data = JSON.stringify(data)
                ajaxRequest(data, 'POST', REQUEST_PATH, function (response) {
                    $('#userModal').modal('hide')
                    $.notify({
                        icon: 'la la-bell',
                        title: 'Bravo',
                        message: "L'utilisateur a été ajouté avec succès",
                    }, {
                        type: 'success',
                        placement: {
                            from: "top",
                            align: "right"
                        },
                        time: 500,
                    });
                    resetHtmlForm('#userForm')
                    userDataTable.ajax.reload()
                })
            }
        });

        $(document).on("click", ".editUser", function (e) {
            e.preventDefault();
            var id = getDataValue(this, "id")
            ajaxRequest('', 'GET', REQUEST_PATH + id, function (response) {
                resetHtmlForm('#userForm')
                data = response
                $("#username").val(data.username),
                    $("#role").val(data.role.id),
                    $("#idUser").val(data.id)
                $('#userModal').modal('show')
                changeModalAttribute('#userModal', 'userEditModal', '#userForm', 'userUpdateForm')
            })
        })
      
        $(document).on("click", ".deleteUser", function (e) {
            e.preventDefault();
            $this = $(this)
            var id = getDataValue(this, "id")

            swal({
                    title: "Êtes-vous sûr ?",
                    text: "Vous ne pourrez pas revenir en arrière !",
                    confirmButtonColor: "#7030A0",
                    icon: "warning",
                    buttons: {
                        cancel: 'NON',
                        catch: {
                            text: 'OUI',
                            value: 'confirm'
                        }
                    },
                    dangerMode: true,
                })
                .then((willDelete) => {
                    if (willDelete) {
                        ajaxRequest(null, "DELETE", REQUEST_PATH + id, null)
                         $.notify({
                             icon: 'la la-bell',
                             title: 'Bravo',
                             message: "L'utilisateur a été supprimé avec succès",
                         }, {
                             type: 'success',
                             placement: {
                                 from: "top",
                                 align: "right"
                             },
                             time: 500,
                         });
                        $($this.parent().parent().parent()).remove();
                    } else {
                        swal("ACTION ANNULEE");
                    }
                });
        })
    });
});