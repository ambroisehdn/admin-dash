$.getScript("/assets/js/plugin.js", function () {

    $(document).ready(function () {
        REQUEST_FILE_PATH = "/api/file/"

        var fileDataTable = $('#fileTable').DataTable({
            "sAjaxSource": "/api/file",
            "sAjaxDataProp": "",
            "order": [
                [0, "asc"]
            ],
            "aoColumns": [{
                    "mData": "id"
                },
                {
                    "mData": "name"
                },
                {
                    "render": function (data, type, row) {
                        return '<div class="text-center"> <a href="#' + row.id + '" data-id="' + row.id + '" class="btn btn-info btn-sm ml-2 editRole"> <span> <i class="la la-edit"></i></span></a>  <a data-id="' + row.id + '" href="#' + row.id + '" class="btn btn-danger btn-sm ml-2 deleteRole"> <span> <i class="la la-trash"></i> </span> </a>   </div>';
                    }
                },
            ]
        })

        $(document).on("click", "#addFile", function (e) {
            e.preventDefault()
            $('#fileModal').modal('show')
        });


        $(document).on('submit', '#fileForm', function (e) {
            e.preventDefault();
            if ($('#name').val() == '') {
                swal("Error", "Veuillez remplir tous les champs", "error");
            } else {
                
                ajaxUploadRequest(new FormData(this), 'POST', REQUEST_FILE_PATH, function (response) {
                    $('#fileModal').modal('hide')
                    $.notify({
                        icon: 'la la-bell',
                        title: 'Bravo',
                        message: "Le fichier a été ajouté avec succès",
                    }, {
                        type: 'success',
                        placement: {
                            from: "top",
                            align: "right"
                        },
                        time: 500,
                    });
                    resetHtmlForm('#fileForm')
                    fileDataTable.ajax.reload()
                })
            }
        });

    
        $(document).on("click", ".deleteFile", function (e) {
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
                        ajaxRequest(null, "DELETE", REQUEST_FILE_PATH + id, null)
                        $.notify({
                            icon: 'la la-bell',
                            title: 'Bravo',
                            message: "Le fichier a été supprimé avec succès",
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