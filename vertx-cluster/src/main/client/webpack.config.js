var webpack = require('webpack');

module.exports = {
  devtool: 'source-map',
  entry: './src/main.js',
  output: {
    path: __dirname + '/../resources/webroot',
    filename: 'bundle.js',
    publicPath: '/'
  },
  module: {
    loaders: [
      {
        test: /\.js$/,
        loader: 'babel-loader',
        query: {
          presets: ['es2015']
        }
      }
    ]
  }
};
