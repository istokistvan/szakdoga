import {ReactNode} from "react";
import Navbar from "@/app/ui/Navbar/Navbar";
import QuizCreateButton from "@/app/ui/QuizCreateButton";
import {Link} from "@nextui-org/react";

export default function AppLayout({children}: { children: ReactNode }) {
    return (
        <div
            className="w-screen h-screen px-3"
        >
            <div
                className="w-full flex justify-center"
            >
                <Navbar/>
            </div>
            {children}
            <Link
                href="/quiz/create"
                className="fixed bottom-10 left-10"
            >
                <QuizCreateButton/>
            </Link>
        </div>
    )
}