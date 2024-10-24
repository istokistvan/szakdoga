import {NextRequest} from "next/server";
import {SecurityFilter} from "@/app/util/SecurityFilter";

export async function middleware(req: NextRequest) {

    const securityFilter = new SecurityFilter(req)

    return await securityFilter.response()
}