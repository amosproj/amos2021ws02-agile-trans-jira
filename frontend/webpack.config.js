const WrmPlugin = require('atlassian-webresource-webpack-plugin');
var path = require('path');


module.exports = {
    module: {
        rules: [
          {
            test: /\.(js|jsx)$/,
            exclude: /node_modules/,
            use: {
                loader: "babel-loader"
            }
          },
          {
        test: /\.s[ac]ss$/i,
        use: [
          // Creates `style` nodes from JS strings
          "style-loader",
          // Translates CSS into CommonJS
          "css-loader",
          // Compiles Sass to CSS
          "sass-loader",
        ],
      },
        ]
    },
    watch: true,
    entry: {
        'dynamictable': './src/dynamictable.js'
    },

    plugins: [
        new WrmPlugin({
            pluginKey: 'de.tuberlin.amos.gr2.agile-planning-platform',
            locationPrefix: 'frontend/',
            watch : true,
            xmlDescriptors: path.resolve('../backend/src/main/resources', 'META-INF', 'plugin-descriptors', 'wr-defs.xml')
        }),
    ],
    output: {
        filename: 'bundled.[name].js',
        path: path.resolve("../backend/src/main/resources/frontend")
    }
};
