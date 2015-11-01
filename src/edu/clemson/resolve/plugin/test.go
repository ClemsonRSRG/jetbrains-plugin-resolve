package plugin

import "fmt"
func bar1() {

}
type person struct {

}
func bar2(x person) person {
	var x person;
	return x;
}

func foo() {

	bar2()
}