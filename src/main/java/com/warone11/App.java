package com.warone11;

import io.javalin.Javalin;

public class App {
    public static void main(String[] args) {
        var app = Javalin.create(config -> {
            config.jetty.host="127.0.0.1";
            config.routes.get("/", ctx -> {
                ctx.html("""
                    <!DOCTYPE html>
                    <html lang="es">
                    <head>
                        <meta charset="UTF-8">
                        <meta name="viewport"
                              content="width=device-width, initial-scale=1.0">
                        <title>127MC</title>
                    </head>
                    <body>
                        <h1>127MC</h1>
                        <p>El servidor web está funcionando.</p>
                    </body>
                    </html>
                    """);
            });

            config.routes.get("/api/health", ctx -> {
                ctx.json(java.util.Map.of(
                    "application", "127MC",
                    "status", "ok"
                ));
            });

        }).start(7070);

        System.out.println("Servidor web iniciado en http://127.0.0.1:7070");
    }
}
