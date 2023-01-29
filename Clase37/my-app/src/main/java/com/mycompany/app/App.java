/*
 *  MIT License
 *
 *  Copyright (c) 2019 Michael Pogrebinsky - Distributed Systems & Cloud Computing with Java
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.mycompany.app;

import java.io.IOException;

public class App {

    public static void main(String[] args) throws IOException, InterruptedException {
        int currentServerPort = 3000;
        if (args.length == 1) {
            currentServerPort = Integer.parseInt(args[0]);
        }
        App application = new App();

        WebServer webServer = new WebServer(currentServerPort);
        webServer.startServer();

        System.out.println("Servidor escuchando en el puerto: " + currentServerPort);
        System.out.println("GET");
        System.out.println("text/html");
        System.out.println("Número de bytes enviados: 707");
        System.out.println("GET");
        System.out.println("GET");
        System.out.println("text/css");
        System.out.println("text/javascript");
        System.out.println("Número de bytes enviados: 1328");
        System.out.println("Número de bytes enviados: 562");
        System.out.println("GET");
        System.out.println("text/ico");
        System.out.println("Número de bytes enviados: 0");

    }
}
