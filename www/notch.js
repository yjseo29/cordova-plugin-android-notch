const run = require("cordova/exec");

const AndroidNotch = {
    hasCutout: function (success, error) {
        run(success, error, "AndroidNotch", "hasCutout");
    },

    setLayout: function (success, error) {
        run(success, error, "AndroidNotch", "setLayout");
    },

    getInsetTop: function (success, error) {
        run(success, error, "AndroidNotch", "getInsetsTop");
    },

    getInsetRight: function (success, error) {
        run(success, error, "AndroidNotch", "getInsetsRight");
    },

    getInsetBottom: function (success, error) {
        run(success, error, "AndroidNotch", "getInsetsBottom");
    },

    getInsetLeft: function (success, error) {
        run(success, error, "AndroidNotch", "getInsetsLeft");
    },

    getInsets: function (success, error) {
        run(success, error, "AndroidNotch", "getInsets");
    }
};


module.exports = AndroidNotch;