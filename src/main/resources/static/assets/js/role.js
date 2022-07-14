$.getScript("/assets/js/plugin.js", function () {

    $(document).ready(function () {
        REQUEST_ROLE_PATH = "/api/role/"

        var roleDataTable = $('#roleTable').DataTable({
            "sAjaxSource": "/api/role",
            "sAjaxDataProp": "",
            "order": [
                [0, "asc"]
            ],
            "aoColumns": [{
                    "mData": "id"
                },
                {
                    "mData": "rolename"
                },
                {
                    "render": function (data, type, row) {
                        return '<div class="text-center"> <a href="#' + row.id + '" data-id="' + row.id + '" class="btn btn-info btn-sm ml-2 editRole"> <span> <i class="la la-edit"></i></span></a>  <a data-id="' + row.id + '" href="#' + row.id + '" class="btn btn-danger btn-sm ml-2 deleteRole"> <span> <i class="la la-trash"></i> </span> </a>   </div>';
                    }
                },
            ]
        })

        $(document).on("click", "#addRole", function (e) {
            e.preventDefault()
            $('#roleModal').modal('show')
        });


        $(document).on('submit', '#roleForm', function (e) {
            e.preventDefault();
            var form_data = $(this).serialize();
            if (form_data.indexOf('=&') > -1 || form_data.substr(form_data.length - 1) == '=') {
                swal("Error", "Veuillez remplir tous les champs", "error");
            } else {
                var data = {
                    "rolename": $('#rolename').val(),
                }
                data = JSON.stringify(data)
                ajaxRequest(data, 'POST', REQUEST_ROLE_PATH, function (response) {
                    $('#roleModal').modal('hide')
                    $.notify({
                        icon: 'la la-bell',
                        title: 'Bravo',
                        message: "Le role a été ajouté avec succès",
                    }, {
                        type: 'success',
                        placement: {
                            from: "top",
                            align: "right"
                        },
                        time: 500,
                    });
                    resetHtmlForm('#roleForm')
                    roleDataTable.ajax.reload()
                })
            }
        });

        $(document).on("click", ".editRole", function (e) {
            e.preventDefault();
            var id = getDataValue(this, "id")
            ajaxRequest('', 'GET', REQUEST_ROLE_PATH + id, function (response) {
                resetHtmlForm('#roleForm')
                data = response
                $("#rolename").val(data.rolename),
                    $("#idRole").val(data.id)
                $('#roleModal').modal('show')
                changeModalAttribute('#roleModal', 'roleEditModal', '#roleForm', 'roleUpdateForm')
            })
        })

        $(document).on('submit', '#roleUpdateForm', function (e) {
            e.preventDefault();
            var form_data = $(this).serialize();
            var id = getTagValue('#idRole')
            if (form_data.indexOf('=&') > -1 || form_data.substr(form_data.length - 1) == '=') {
                swal("Error", "Veuillez remplir tous les champs", "error");
            } else {
                var data = {
                    "rolename": $('#rolename').val(),
                }
                data = JSON.stringify(data)
                ajaxRequest(data, 'POST', REQUEST_ROLE_PATH+id, function (response) {
                    $('#roleEditModal').modal('hide')
                    $.notify({
                        icon: 'la la-bell',
                        title: 'Bravo',
                        message: "Le role a été modifié avec succès",
                    }, {
                        type: 'success',
                        placement: {
                            from: "top",
                            align: "right"
                        },
                        time: 500,
                    });
                    resetHtmlForm('#roleUpdateForm')
                    changeModalAttribute('#roleEditModal', 'roleModal', '#roleUpdateForm', 'roleForm')
                    roleDataTable.ajax.reload()
                })
            }
        });

        $(document).on("click", ".deleteRole", function (e) {
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
                        ajaxRequest(null, "DELETE", REQUEST_ROLE_PATH + id, null)
                        $.notify({
                            icon: 'la la-bell',
                            title: 'Bravo',
                            message: "Le role a été supprimé avec succès",
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