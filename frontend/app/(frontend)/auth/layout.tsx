import {ReactNode} from "react";

export default function AuthLayout({children}: { children: ReactNode }) {
    return (
        <div
            className="grid h-screen w-screen overflow-hidden p-5 lg:grid-cols-2"
        >
            {children}
        </div>
    )
}