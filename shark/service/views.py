from flask import Flask, render_template, request

from static import project


app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def app_info():
    if request.method == 'POST':
        app_name = request.form['app_name']
        pkg_name = request.form['pkg_name']
        project.make(app_name, pkg_name)
        ret = 'sucess!'
    else:
        ret = 'error!'

    return render_template("index.html", title='Home', ret=ret)


if __name__ == '__main__':
    app.run(debug=True)
