import {NextRequest, NextResponse} from "next/server";
import {AUTHORIZATION} from "@/app/lib/definitions";

export class SecurityFilter {

    private readonly authorization: string
    private readonly url: string
    private readonly origin: string

    private publicUrls = ["/_next", "/dashboard", "/api/auth/logout"]

    constructor(req: NextRequest) {
        this.authorization = req.cookies.get(AUTHORIZATION)?.value ?? ""
        this.url = req.nextUrl.pathname
        this.origin = req.nextUrl.origin
    }

    async auth() {
        const res = await fetch("http://backend:8080/api/auth/validate", {
            method: "GET",
            cache: "no-store",
            headers: {
                "Authorization": `Bearer ${this.authorization}`
            }
        })

        return res.status === 200

    }

    async response() {
        const authenticated = await this.auth()

        if (this.publicUrls.some(url => this.url.startsWith(url))) {
            return NextResponse.next()
        }

        if (authenticated) {
            if (this.url.startsWith("/auth")) {
                return NextResponse.redirect(new URL("/dashboard", this.origin))
            }
        } else {
            if (!this.url.startsWith("/auth")) {
                return NextResponse.redirect(new URL("/auth/login", this.origin))
            }
            return NextResponse.next()
        }
    }
}