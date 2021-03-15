package com.tt2.mentionsample

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.tt2.mention.util.MentionUtil
import com.tt2.mentionsample.model.MentionSampleModel

class MainActivity : AppCompatActivity(), MentionUtil.ClickCallback {
    private lateinit var input: EditText
    private lateinit var result: TextView
    private lateinit var button: Button
    private lateinit var mentionRecyclerView: RecyclerView
    private val sampleUrl =
        "data:image/jpeg;base64,/9j/4AAQSkZJRgABAQAAAQABAAD/2wCEAAoHCBYWFRgVFhUYGBgZGBgYGBgYGhgYGBgaHBgaGhgZGhgcIS4lHB4rIRgZJjgmLC8xNTU1GiQ7QDs0Py40NTEBDAwMEA8QHxISHjQhJCw0MTQxNDQ0NDQ0MTQ0NDQ0NDQ0NDQ0NDQ0NDQ0NDQxNDQ0NDQ0NDQ0NDE0NDQ0NDE0NP/AABEIAOEA4QMBIgACEQEDEQH/xAAcAAABBQEBAQAAAAAAAAAAAAADAAECBAUGBwj/xABEEAABAwIEAwUFBwAIBAcAAAABAAIRAyEEEjFBBVFhInGBkaEGMrHB8BMUQlJi0eEHM3JzgpKy8RUjNNIWQ1Ois8LD/8QAGgEAAwEBAQEAAAAAAAAAAAAAAAECAwQFBv/EACURAAICAQUAAQQDAAAAAAAAAAABAhEDBBIhMUFRFCIyYQUTsf/aAAwDAQACEQMRAD8A9dYZ1OiIHiYlQpt+iitTJRJOkmKRQOoq75VtyrVTB0TREio8X8UKs1Gfc9dVCqFRLMyuxchx7h2Ul7Rb8Q5dV21ZqzMTTDhBuFtjltdmclaOChKFc4jgzTfH4Tdp6cvBVIXemmrRzvgaEoTwlCYDQlClCUIAjCUKUJZUARhPlUsqQCAI5UgFOEoSAhCUKcJQgCEJ4U4ShAyEJ4UkoQIjCdPCSAPZ2Se5FLgN1ANkIDWR/K8g77ouAp1WpuhGaTuEh2TQntRCgNDpvEeqAYGo1Ce1WnhV3hUiGVKjbLPrUrrTeq1ZqtEM5/iOED2Fp8DyK5SpTLSWkQQYK7x7dlg8awOYZ2i416hdWGdOmYzj6c9CUKYCQauszIQnyogCZICOVIqcJsqAIQlCnlTwgCEJQpwlCAIQlCmQlCAIwlClCUIAjCfKnhKEgGhJShOgD2GndFAVNlZrfxt85+CmMU383ovIo70y3CSqjFN/MfJO2uDpm+CVDsspFV3VOjvVQ+3/AEu806CwlRm6A9qi7FD9XmhHFN6+QTSIbRCoxVXP5hWnVmn8XmEJ7wdCCrRDKNUxroq72hXKjOirO+CtCZy/FcDkdmb7rvQ8ln5V1+JpB7S1wgEfRXL1qJa4tO3r1XZhnuVMwlGmAypZVOEoWpJDKllU4ShAEMqUImVKEADATwpQlCAIwlCnCUJAQhKFPKllQBFJSypQgCKSlCSAPT6PCGbknporrMI0WjzJKOCnXkWzvSRAUwNh5KYCdJIoZOkkgCBaOSDVoNP4R5KVSu0EiZdE5Rd3kqNWpXdpkYOsvd6QFUbIk0uxPwjeSqVsINZPxRnNq652HvZHwKqOxrm++wx+dnab4jULRJmLlF8WCdQcNHKu6o4WcJ6rRbUaRmaQ4bEQUCuJCpMGik4jw6rK4nhZEjUaHmOS1iAbH+f5VZwi0+C0g9rshq0c3lTQruMowZGh25FVsq64ytWYtUDypQiZU0KhEIShThKEAQhNlRIShAwcJwFOEoSAjCUKUJQgZGEoU4SypAQhJTypIA9baVJefO9o8Y/3GNaOcID8diz7+IDekwfReVtO5SPSFA1ANSB3kLy+rXMS/EuceQk3RH46GNZmdpmFgSc3M+CNo7PR342mNajB/iCz6/FWvd9nScCYl7xcMHzcdvNeeU6rnvaybOIHaAHzXZN4bEf814hobYiABAA00RSInk2oJjeKYeha2YXLiCXE8ydyucx/t1RaSPtHHmGhYvtvlokEuFUvJlpcQQNQcwsbQuLFSg7XDH/DUPzC0jSIipSVs9Bpf0h0NDUdH6mmFvcO9paNUSx4PcbHp0K8nZw7DO/8uq3ue13xCsYfBUqRzsqVGEfoDgRyIBuFSlFMjLhtfb2enBxYc7GwSSXMJa0O3s0n3uo1WiKzHtDg4XEgTB8lgcF4j94ZDSHiwlpLHyJ1BSxWCymHNe1pu02JzR2gL6GJ8DzSk1dozxSae2RtPpcoKzcSwzKzabBs53p8nI7ftBo95Eci8eUJqaNnFh6zA5qy6rA25Ia3YuMA9BufALc4dVhpqVG3BLQ1zC0EhouW6kX5Lm+J4Z7y6oXB9zJFiI5NO3QFdGGSb7pHPmlt8tibimEwHt8czR5kI7mRqsEsH1qj4Cu5jgyZY4xE+6SQJHLW66pRpWnZjHJudNUa2VKEZzCDB1GqbKps0BQlCJlSyoBA4ShEypZUhg4SyooallQALKllRgxINSsYHKkjZUkWBUxOIfE5nHoP2Co/fW7h09R+61HsQX0QdQCvLO4ptxzOo8FYZxGnl7QzC8OEhw6Tv4pfc2a5firWFaGCGsYepaCUABxGRrW1GudEOlpBD5EGQOV10WB9oaP3V1QAhzWhrg4Xc8tgHW4MeiwOJYrNLnloIY6LW2GngFg4txbTAF5gkbE7I7Rm47pGXxnFOrPLnctNABsIVPDuymSJHgjvpOd4n+UzqZFoVrqjZcKjSZXZkzaD1VRuMa50AW5kgIVacmWwvOgnunkqjKB77KKHSPRv6O8B2XPggFzoeCIj3Q3v1PgF1XE8K7ISHSGw+CJJymTcdJGm6x/6OKs4bJPuuJHc7+QfNdJjGuLHhpDXFrgCRIBixhaxVxPJzSrLf7OHx1MMe5vJxA68lVAEEjbWFsYmu7XK1r3BudwAk9kQByER3rPq05uSev7kbrI9OrN7h7SykyxIIBJBMtJN951KsVmvI9+HcwXEEdWmLqPs0C7DNJMjM+MwiQHmD0lHdRAN2kEzvIMn8wXVBJHiaictz5OX4lRqZ4ZiXtgXa4l411kSR4tWbVrYhnaOV4F8wY12UjdwLQ5p7wF2GIwjXXcwE8zr56+Kw35qT3NLHuaSC0ucXjTRrybdzo710RRlHO0qAUeKZ2B725nEkOMkGRefIjZFfiWBxbe1r84mFQrPpBj3gkOa9hDHsyEl2YOkAAGwF/ispuIOYOuRMnq7YAc9T4LjyZpY57UeipboJnSVK0OA5/uAfimfXhubpPiQI9Sst2JJDSLwfE7Dw08kR1aY5AA/5Ra3eW+Stai7GkatN0wN0SFm0q0XJu6wE/U9/VWPv4Bg6Rc2sqWojXImWgFLKs2rjtQ1wJBsdjHPl9clJnEAWzoYMg/t9d11P1cboZffACBiK7WNJJ0AWRieLBzHCIIt3tP1Ph1WVjsWX9ibZgTf57gfILOWrXgG/wD8QH/qM8ky5v7U8v8A2hJR9UwO1LFEsV5zAo/ZhTR3FIMRMkI32aiWJgYvGsNmLNSJMgTeIMLK4wSCRyAtyMCV0+LYYBAu0yPn6ErnseMwJjUx47pXTF6ZbHwGd6sYhzSYy35XuqeNboOXxVunTD2gkSeliDzR+zVUSZSYbAOnrYeuqhxVjabA1kl7xf8ASzfzSxFRlMZjL3/hDjMfsPih4d5e8uJ1iZ17uimyX8nV+yWKFJ9ADSp2D4i3qAu/xDuy7uI8TYLzzhVQAtLYJYZaOThG267OpxCaTXEFrnT2dxlOvcTBW8eIWeXljuzJIyMZGZ3fHlYfBY2LrvFQNBAYA0uEXOaRY98LXIlUsfw9zyHMIBgtM7ixae8EeqyZ6iRs+zPEQ5v2Lj2mjsdW6x1I+C2nt8lwQxDqZa+Iyw4d/I/Bd+82nmF1x6T+Tw9THbJsoV6VjDiD6d3cs57HiTINrQbLVqKhihG+o0XTjPGzS7OQ9qKgLGyIdnaLzMBr7fBYGHrCYBBPW/0eq1va/iFMPbTe0uIYXWMBrnEZLcoaT4hcy3FjS7b2gxC8jVPdlbR9Bp4NYY38G1keZMW3ABjvsnpvcDlBGY5Re0AC3x9AsYYggl03J967e+7SrHC8S4BxzmXOmCS5rpiQ6T0HksU2jdROjpsLbwC46mZiByFxuqtd7jLhoIk79NNRb6unHEm5SAIcbHJIaR1l3rpfQJzi8oLhEuaQZ7QcNx5gc+acpXwxyjEzcRVLYNhOhbYGJk9FB+Kdz3np1hPxaox16bcoMZm/hmDcXPVZpfYa3PwWTRG0vVTmgzeIjv8A4UcNRuSXRG2vNVGVDOqP94bHKPPy3S5QU+g+VvM+n7pKn9+HL0P7JI5HsPVcqiWhFhLIvQOwAWpZEfKk9IZTqtWPxXD5RnaOydf0n9iturdV3jbbQjbuhJqwOErMk9fgrjcPAEOI9R5LTx/Bz7zNjOU7dx/dUXSAWuBHeDZCRfhlVGguk3jmreGoGZA3CIxjdbQBI017lfwWHMNgEwCTsJPU6pNembbfESnh6mV7iDo4/KQulweJblAL2k7iRbouTxDCXHaTKQYbCFEs6rajpxaJ3ul2zvKTgdCD3FWWMVb2O4U0t+0eAQT2WxrG5O46Ld4tjqbBla1ubuFllLURjG2W9LJz2x5MKvw45iWxDtjpJ1HzW7g3k02dGxPdbfuWXg+IB5yui+kc9loYnGNpU3PeDlaJlsTc8jAOq6tNqo5I8dI8z+S0M4v9/wCkatcAkZuR3+tisfimMYwPqPMNY0SREk7Nb1Og/hVcb7YUGtJa2o//AAhgHeZJHkvPON8YqYh/bIDG3Yxs5Wzqb3c4x7xW8tVFKo8s8bFonKe6fC+Pkp4+u+tUc93vOMnkNg0dAAAEJlFxnMBA6gdJCt4ekNDfTpylGrj803Np07gF57lbPVvwqNoOaZBIEgWtbofkVaZVL3G4BgOzNbkuP0gATpcC5lAbiIcJFhaxMi0XHj6KbH5XDeDyvHIT4pcj5NPB4Z3vO0mDJuZvZu9r+KbEPEQ0et1U+8SDsTqNDvsUNjjME266E96iRMn8D1DOsoQbtyuFcytGpER1/wByjNqNaCQ2wBLj2ZI0DQDIBnpIUxfgR75Mp7YibTcecfFOyYJDSTzIJ/gFDr137QDvDW/NJ9dzrl5Ji8/uR0WlGlEs7+vmkqf2n1ITIoKPcQE8KcJoXYdBHKoPCMhuSGVnNQ3MVotUcqBoquYgOpT1WjlQnMSY0UmUANGjyCVVnormRIM2UsuLpnMPoAuIi8nxndSpYPeNLFdGcG0mSLpHBiYAgLmlil4d8dRGlYLD44sDGiwAi+noiVR9pLp70Orw1xHMA259yKwQMgaZ5AEriy457qo7MebHt3J8gKNOHsLR+IfFXfa6sW0MgbOc5TGoABd8ld4XgS3tv1/COXXvQPaOgHsGwaZM3gQd12YMUoY35Z5H8hm/tdR84PLMW0g2Jb5+oWecOSSfMbayCP2XZ4rhjCHPBgNbo4gOc/k0RIHut758VR9ma2TPAgGXtjSSOztIjWNLSdYahJHmQxSTOLqyBa1tzrv3oYv+MGdbtJ+K61/siHNe99QgsDYIy9p7gbSbNAymflCo8N4CH1Ya7PTDSS4EZozBrbC5cdmj5wdVHg0WNlHCU2lpBk2l4GuXXMBFwAD6HZBqUsrgD5yJIOhmb/JX/aIBjxSkQwZWOESejr2N4PjZYhqQLi405x0Sa8Jkq4DNBJmR4kAeSZ2LAsPe0B0v1P7KkZJUc3z7wpUSVE0qVSep36bDzKtVGgUzuS15N7C0N8ZdPisRtYs7PPU7xy9fRblCswsYf0ljswEAvzNMdIIMneUbaYbaZTFMPAuA7l13M7AkE+aBVpvbMiYsdyO+LotF+R1gMzCZa4SDGoIOv+ymKw90jMIFjqPEXBTfA+jOzj8v+pJacUub/wDMP+1JLcvgLPZUgnLU4aus6BMbNlOpQ6JNCODKBlBzFFjFcqU0JrEhojkQ6lNW2tUHMlAIq/ZpxTVkMUYSoYMNSDOaKGqbWIodkGBHY1JrEZjExNhGtVDizIpPdE5QDpMXiY5gSVoC1lzvthxVtGkWuzS+WiIgQCZN51AuAUpPghnBY2s/PkY8iYBMgTHPmLo//iSs1gY1xbltt2p5875pJ/Meaz8RjGlwhwO4sJHYYSCCJ1keEqtWIk2trtHeOi5m2jncpR4BDiT/APmMc4lrxBGbQySDfa7pFtVp8K4qWhwsHZWsaSZIAN8uazDZvaGgasapTm8JUpFx9cx8foKlIcZtGthqbHl731Mt8rM4JzmNzBhtpg7FY+OojNYyBuAD8DotjB8UDGODza5AAbJFs1yLWAHh1VPF1M7Q5rZtJc0AXJMzaRBtbom3xZUmmrMfLyumeVKrE7jqZn+VACdL92vkmSgL+ikyoQwjaQfEaKL2SbJYak59t5A0vzhHBaVhG3Oc8ififkitmO8mSp/ZXA6X8JBTsbMDmf5J9Em7JYP7ueY80lchv5XeQSU2Se3hqllUg1EY1dJ0A2UypZYVxjRsk+n08tAgZUnmmdTRsqTrIAH9mohidzye5RL0rHRN9IeKEWQpBx5pFFjIFqdoTwpNamARgU22TNameUCYi9efe3OLY9zgHtmnlYWxLi5wzkdBGW/+x7nG1wxj3kTka50c8omPReFPxDqj3PebvcXuPM3JnkLqJdGbYF9UufmtM+HQXWizTcd8gevxCqOZObsuA0JAJA1IHSSFZwjxEiR0kuHSWugg9QsZIykgzm7+feghkT9dFcYJBdmbAuZy5okTLZB320QqgDb6jYj4XUGVGfiAdtvXmiseQ2AABEzuek7eCK9odPPlog1B2dxfwFvj0700aRKVaeqj3lXGszwB7xtB3O8fsn4PUDKpzMBvqR7sDlpra61XRSRWYzLcg6jVXsJhYdmbJDTmJGx0uO8gKfGsP24zNdmh0sgtM3kQdPJVmPIlu+pHhceqllKVMnXm8xfNMR3zHghtF47h4DVTLbDr8B/KekzkJMafPuU3SM2x8h5nzKSl9m/mf8pSUWybPcQiNKEFMLtOosMep5kBqlmQMkSgVHqT3IDkmNDEqLinKESpZSJNel9p1Veo+N0E1lDlRooWaIqIjHglZJxUIGJ4mWNJ32G87KXlUVbLWFydI1eJcSbSZmjMdAJjzKx+H8fc+oGPaBmNiNuQj5rErYp7zL3SeWw7ghUqZc4Ec/qFwS1cnP7ejujpIqD3dnSe2WOFPB1Xbubkb3vOX0BJ8F43Tbfpp8wvQv6Q8TDKFAkOIGd4JFyBlaTvN3LhKDAHBomTsY8Lr0HKzwp/k0a/D8O59tyS6ORE9ojpJW5ieHBzWuDWAQMsdnLAiNZdqTOkz3rV9luCGA99gbRqdJOnw7ua6T/hpcHVHMAmzGaQBOUEjTcnlKKdDUXR5xXwgb2TOYwRLjEWg9pplUH0oB7LfMfAC69AxPAXk9TYwN4vrbXyWBxHgbg/tOsPd2sNLjTuss3F+mTizmcNUIdGYi8loNpFgb2t3LUp4sBpa5ge065nZSQCCNAZIyuHlonr4VobY3BHZix5yZudFQqhpu42OhvrvIm26LaE3TCmjQqWhzCG+8DPaBJD4iYiBlHLVYmTM9xkl0OJNrmHSZ81pU8zHSXNI8QIkSb7oLafacD7wOUnnfVUpcF3asFRaLn8sm/SNvrRCpMzOLjofT6CPWuSwHlJ200PIAKAM9kWaNSbTG5/ZJsTEe1pppKmWW1J55bk+MwnazSQSfr8P7ogcIgkNBgQSNv06KWZsFlHJ/mP3SU/tW8/RidIKPbcqk2U4CS7TrHlIpJigYNzkFz0R4UMiQ0QJlQIR8iYsUtFIy8ZUDQXHQLn6uKqO7Ulo5Dc8lu8aokssJ7QWVXokFrdh8d15OsyyjKlwerpIRcbfJTbiqnM/H4oTiTczPVbZoDLlEEx2uQ7lWdg5vy2C4/7XLs60orwz6dKVu8GwUnMRZvx5KGDwRc6NAN+S0OJuyUwxtpPiYuT8F2aTA5y3Po5NXnUIuK7PP8A25wT2VnVM+dr3QCYBbYkM7gB6FcuxpnNsIk7ea9JxNIPY5hgyCJIBiRrfdYmJ9nHMYX0znEEPbqSNy0HQjqvUlDa+Dwkrkek+yAbVw9Oo0ZW5bbCQYI7gQRO8cl0TaQmTpt+5Xk/sFx84at92quc6hUIFMwTleTawvDpAjmQdyvWW1g4iDtPwgepTXJuBrYeZI5edwT6BczjqbHPh8lomW952j5LqcRPO3JUWYdrj7thrYEeE7pNCaPO+M4UMkFpymSwWlvKRaerguTxB0AAuJI0gukAE6civU/aRlJjHE0zE27XZJvs4loNuS8n4nZznNsC4kDUtkyAOYGxjZZySswlFWPiHAPjfK10TIcC0FwPJwkfQUqhkl2xy6WJtrAVN9N1R4a09qNZs2Nz0v8ABRr4qDl5dkkbbwNfFG0cY/awriIgbkkkQJJ2B5CEmuLdh43+H7qlnI274F+dwiMqnr/lgpOJm0WftOh8IaPLdOa5aDLXRI5x8EHO79XS5HzVfEk8z5lCiCiW/vA+i/8A7UllJKtpVH0blTqLXSpNHVdBuNCZyJ01USxAA4TQigJBqABJOYi5VLJzSY0U3UQbEWKo1OCMJkFw6ahbUJlhkwQyfkrN4ZpQ/FmOeGECzh4hPR4cBqZ5xYLWypFiyjpMSd0aPVzaqytToBosLLI45q3uM+i33LmuJul58l1wio8I5JzcuzOhSoVCx0x6wdIkHYpwEzmrVqzHo4/2pw72PbVDQ1pNnMmzh+J35Xk3mbmdNFs+y/tRWa4Pqy9pFNkjsuDWF06fiyvc2+pjYLVrYdr2OY8S1zSD8j3gwfBZmE4a+m3I1mhgw33ifxWsspLb0WpNnqnD8R9sxr9Q4SCNI+vrZXfsgBYDvXC+xXE3NJpPc1rAC5mWTmIIGXMdAPyjnrAXaVsWGwNXQSY0AEST4kDxU9mhie0WAJYSGvcTawBEHnOi8x4rwrKS10S42Gh1iN7d5Xq/E+IM+zLi62UkxawsbnS8DxXkOOxTXve/L7uYNEg9qLXgTcz4qXHkzklZnGabXsae2RD3R7o2bPMkHuusatTJubSc084sYCtvp1CCBJlxcSDvpzmUL7uZgtfPMnfVUlXJLB5Cb9APkrVOha1jHgbhb/BuBZxnIEBskDYSABOkyfQpsTgwxjmxeRBuIt87fws3bJpmXh6M7fug42hA+B79Fa4ebl0idQNi0Dt9ZiNNjeIQuOYkWh7XmItc7bi0CSPApJOwoyPD0SQ/tz+U+n7JK6Cj6MZ8lJqdJbGw40UmpJIAcpikkgBwkUkkhkSotTpKUNDlRGqdJNCB1dFzvEPePeEklUSJFQqDkklZASjquhwPu0/rdySSzkVHsz/Zr/qMR/ds/wBDVrUffd/dM+L06SiPRqzB47/VD+yf/kYvP+Jf1Tv77/8AMJ0kMzZXpf1bu4/FqzeGbf2nf6Qkkh9Evo7/ANnvxdzP/osnjXu1P7LP9LkklAeHN0/+nf8A2D/qCxaGvl8AkkmHhaSSSVAf/9k="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val list = listOf(
            MentionSampleModel("Tina TT2", sampleUrl, 0),
            MentionSampleModel("Sara", "", 1),
            MentionSampleModel("John", "", 2)
        )
        initUiViews()
        initMention(list)
    }

    private fun initUiViews() {
        result = findViewById(R.id.textView_result)
        input = findViewById(R.id.input)
        button = findViewById(R.id.button)
        mentionRecyclerView = findViewById(R.id.recyclerView_mentionList)
    }

    private fun initMention(resultMemberList: List<MentionSampleModel>) {
        MentionUtil(
            input = input,
            button = button,
            recyclerViewMentions = mentionRecyclerView,
            memberList = resultMemberList,
            context = applicationContext,
            callbackListener = this,
            placeHolder = R.drawable.all_avatarplaceholder,
            prefixConvert = " ***{user_reference_id:",
            postfixConvert = "*** "
        )
    }

    override fun callback(convertedText: String, boldText: String) {
        val text = "Converted text: $convertedText \n\n Bold text: $boldText "
        result.text = text
    }
}