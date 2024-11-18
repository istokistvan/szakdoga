import type {Metadata} from "next";
import {Inter} from "next/font/google";
import "./globals.css";
import {ReactNode, Suspense} from "react";

const inter = Inter({subsets: ["latin"]});

export const metadata: Metadata = {
    title: "Quizlet",
};

export default function RootLayout({
                                       children,
                                   }: Readonly<{
    children: ReactNode;
}>) {
    return (
        <html lang="hu">
        <Suspense fallback={
            <body>
            <div>Loading...</div>
            </body>
        }>
            <body className={inter.className}>{children}</body>
        </Suspense>
        </html>
    );
}
