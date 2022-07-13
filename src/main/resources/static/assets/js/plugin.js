    $('[data-toggle="tooltip"]').tooltip()
    function formToJson($form) {
        var unindexed_array = $form.serializeArray();
        var indexed_array = {};

        $.map(unindexed_array, function (n, i) {
            indexed_array[n['name']] = n['value'];
        });

        return JSON.stringify(indexed_array);
    }


    function ajaxRequest(data, method, url, callback) {
        $.ajax({
            type: method,
            url: url,
            data: data,
            dataType: "JSON",
            contentType: "application/json; charset=utf-8",
            success: callback
        });
    }

    function ajaxRequestBasedHttpStatusResponseCode(data, method, url, callback) {
        $.ajax({
            type: method,
            url: url,
            data: data,
            dataType: "JSON",
            contentType: "application/json; charset=utf-8",
            error: callback,
            success: callback
        });
    }

    function ajaxUploadRequest(data, method, url, callback) {
        $.ajax({
            url: url,
            dataType: 'JSON',
            cache: false,
            contentType: false,
            processData: false,
            data: data, error: callback,
            success: callback,
            type: method,
            success: callback

        });
    }


    function response(response) {
        if (response.error) {

            if (typeof (response.message) === 'object') {
                errors = response.message
                Object.keys(errors).forEach(function (key) {
                    alertify.error(errors[key][0])
                });
            } else {
                swal("Erreur !", response.message, "error");
            }
        } else {
            swal("Bravo", response.message, "success")
                .then((value) => {
                    location.reload()
                });

        }
    }

    function callBackAction(modalToClose, dataFunction) {
        $(modalToClose).modal('hide');
        dataFunction
    }

    function responseWithDataLoad(response) {

        if (response.error) {

            if (typeof (response.message) === 'object') {
                errors = response.message
                Object.keys(errors).forEach(function (key) {
                    alertify.error(errors[key][0])
                });
                return false
            } else {
                swal("Erreur !", response.message, "error");
                return false
            }

        } else {

            swal("Bravo", response.message, "success")
                .then((value) => {
                    callBackAction(response.data.modal, eval(response.data.function))
                });

        }
    }

    function responseSwal(response) {
        if (response.error) {
            swal("Erreur !", response.message, "error");
        } else {
            swal("Bravo", response.message, "success")
        }
    }

    function responsewithData(response) {
        if (response.error) {
            if (typeof (response.message) === 'object') {
                errors = response.message
                Object.keys(errors).forEach(function (key) {
                    alertify.error(errors[key][0])
                });
            } else {
                swal("Erreur !", response.message, "error");
            }
        } else {
            swal("Bravo", response.message, "success")
                .then((value) => {
                    location.replace(response.data.url)
                });

        }
    }

    function responsewithDataBasedHttpStatusCode(responseServer) {
        responseLocal = (responseServer.responseJSON);
        if (typeof responseLocal == 'undefined') {
            responseLocal = responseServer
        }
        console.log(responseLocal)
        if (responseLocal.error) {
            if (typeof (responseLocal.message) === 'object') {
                errors = responseLocal.message
                Object.keys(errors).forEach(function (key) {
                    alertify.error(errors[key][0])
                });
            } else {
                swal("Erreur !", responseLocal.message, "error");
            }
        } else {
            swal("Bravo", responseLocal.message, "success")
                .then((value) => {
                    location.reload()
                });

        }
    }

    function swalAlert(title, text, obj, data, methode, link, response) {
        $this = obj
        swal({
                title: title,
                text: text,
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
                    ajaxRequest(data, methode, link, response)
                    $($this.parent().parent()).remove();
                } else {
                    swal("ACTION ANNULEE");
                }
            });
    }

    function changeModalAttribute(oldModalId, newModalId, oldFormId, newFormId) {
        $(oldModalId).attr("id", newModalId)
        $(oldFormId).attr("id", newFormId)
    }

    function resetHtmlForm(formId) {
        $(formId).trigger("reset");
    }

    function getTagValue(tagId) {
        return $(tagId).val()
    }

    function getDataValue(element, attribute) {
        return $(element).data(attribute)
    }

