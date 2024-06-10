$(function () {
  $("#autocomplete").autocomplete({
    source: function (req, resp) {
      $.ajax({
        url: "http://localhost:8080/employee/api/search",
        type: "GET",
        dataType: "json",
        data: {
          query: req.term,
        },
        success: function (response) {
          console.log(response);
          resp(response);
        },
        error: function (xhr, ts, err) {
          resp([""]);
        },
      });
    },
  });
});
