import {ReactNode} from "react";

interface MenuItem {
    display: ReactNode;
    href: string;
    classNames?: string,
}

export const menuItems: MenuItem[] = [
    {
        display: "Dashboard",
        href: "/dashboard",
        classNames: "text-[1.2em] text-yellow-400"
    }
]