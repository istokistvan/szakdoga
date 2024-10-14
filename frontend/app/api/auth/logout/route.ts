"use server"

import {cookies} from "next/headers";
import {AUTHORIZATION} from "@/app/lib/definitions";
import {NextRequest, NextResponse} from "next/server";

export async function DELETE(req: NextRequest) {
    cookies().delete(AUTHORIZATION)
    return NextResponse.redirect(new URL("/auth/login", req.nextUrl.origin))
}