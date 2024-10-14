"use client"

import {Link, Navbar as Nav, NavbarContent, NavbarItem, NavbarMenu, NavbarMenuToggle} from "@nextui-org/react";
import {useState} from "react";
import {menuItems} from "@/app/ui/Navbar/menuItems";
import {FiLogOut as LogoutIcon} from "react-icons/fi"
import {useRouter} from "next/navigation";

export default function Navbar() {

    const [isOpen, setIsOpen] = useState(false);
    const router = useRouter()

    const handleLogout = () => {
        fetch("/api/auth/logout", {
            method: "DELETE"
        })
            .then(() => {
                router.replace("/auth/login")
            })
    }

    return (
        <Nav
            onMenuOpenChange={setIsOpen}
            position="static"
            className="w-full shadow-xl my-3 rounded-2xl lg:w-3/4 border-x-1 border-b-1 border-color-[#e2e2e2]"
        >
            <NavbarContent
                className="sm:hidden"
            >
                <NavbarMenuToggle
                    aria-label={isOpen ? "Close menu" : "Open menu"}
                />
            </NavbarContent>

            <NavbarContent justify="end">
                <button onClick={handleLogout}>
                    <LogoutIcon className="text-2xl text-red-500"/>
                </button>
            </NavbarContent>

            <NavbarContent
                className="hidden w-full sm:flex" justify="center"
            >

                {menuItems.map((item, index) => {
                    return (
                        <NavbarItem key={index}>
                            <Link href={item.href} className={item.classNames}>{item.display}</Link>
                        </NavbarItem>
                    )
                })}
            </NavbarContent>

            <NavbarMenu
                className="rounded-2xl mt-5"
            >
                {menuItems.map((item, index) => {
                    return (
                        <NavbarItem key={index}>
                            <Link href={item.href}>{item.display}</Link>
                        </NavbarItem>
                    )
                })}
            </NavbarMenu>
        </Nav>
    )
}