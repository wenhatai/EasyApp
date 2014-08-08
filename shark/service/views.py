from flask import Flask, render_template, request

from static import project

app = Flask(__name__)


@app.route('/', methods=['GET', 'POST'])
def app_info():
    if request.method == 'POST':
        app_name = request.form['app_name']
        pkg_name = request.form['pkg_name']
        load_url = project.make(app_name, pkg_name)
        return render_template("index.html", title='Home', load_url=load_url)
    else:
        return render_template("index.html", title='Home')


if __name__ == '__main__':
    app.run(debug=True)
