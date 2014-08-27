from flask import Flask, render_template, request

from project import project


app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def app_info():
    if request.method == 'POST':
        app_name = request.form.get('app_name')
        pkg_name = request.form.get('pkg_name')
        check_list = request.form.getlist('checkbox')
        load_url = project.package(app_name, pkg_name, check_list)
        return render_template("index.html", title='Home', load_url=load_url, module_list=project.MODULE_LIST)
    else:
        return render_template("index.html", title='Home', module_list=project.MODULE_LIST)


if __name__ == '__main__':
    app.run(debug=True)
