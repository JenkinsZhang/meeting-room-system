const webpack = require('webpack');
module.exports={
    lintOnSave: false,
    devServer: {
        proxy: {
            'api/':{
                target: 'http://localhost:10086/',
                changeOrigin: true,
                ws: true,
                pathRewrite: {
                    '^/api':'http://localhost:10086'
                }
            }
        }
    },
    configureWebpack: {
        plugins: [
            new webpack.ProvidePlugin({
                $:"jquery",

                jQuery:"jquery",
                "windows.jQuery":"jquery"
            })
        ]
    },
}
