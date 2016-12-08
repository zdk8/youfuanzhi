define([], function () {
function html_encode(str)
{
  var s = "";
  if (str.length == 0) return "";
  s = str.replace(/&/g, "&gt;");
  s = s.replace(/</g, "&lt;");
  s = s.replace(/>/g, "&gt;");
  s = s.replace(/ /g, "&nbsp;");
  s = s.replace(/\'/g, "&#39;");
  s = s.replace(/\"/g, "&quot;");
  s = s.replace(/\n/g, "<br>");
  return s;
}

function html_decode(str)
{
  var s = "";
  if (str.length == 0) return "";
  s = str.replace(/&gt;/g, "&");
  s = s.replace(/&lt;/g, "<");
  s = s.replace(/&gt;/g, ">");
  s = s.replace(/&nbsp;/g, " ");
  s = s.replace(/&#39;/g, "\'");
  s = s.replace(/&quot;/g, "\"");
  s = s.replace(/<br>/g, "\n");
  return s;
}

    return {
        render: function (local, cb) {

            var btnAdd = ['add', '添加', 'add'];
            var lo = local.find('div[hvitid=body]');
            var count = 1;
            for (var p in cj.button) {
                if (!/Formatter/.test(p)) {
                    lo.append(count++ + " 按钮类型："+ p+ " 示例:");
                    lo.append(cj.button[p](btnAdd));

                    var $prev = $('<pre></pre>');
                    $prev.append("var btnAdd = ['add', '添加', 'add'];" +
                        " local.append(cj.button."+p+"(btnAdd));")

                    $prev.append(html_encode(cj.button[p](btnAdd)));

                    lo.append($prev);

                    lo.append("<br><hr>");

                }
            }
        }
    }
})