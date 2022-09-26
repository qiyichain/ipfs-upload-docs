/**
 * author: yqq
 * date: 2022-09-26
 * description: golang 实现nft.storage上传和状态查询的demo
 */


package main

import (
	"context"
	"fmt"
	"os"

	nftstorage "github.com/nftstorage/go-client"
)

const (
	ACCESS_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJkaWQ6ZXRocjoweDYzOWU4QzljNkY4YkJhMjQ4NGFFRkE2QzI3MjY5NkY0OGZjZjk2MDIiLCJpc3MiOiJuZnQtc3RvcmFnZSIsImlhdCI6MTY2NDE1NTA3OTU4OCwibmFtZSI6InRlc3QifQ.LRDCI3XT_pMKzdl19kbjPcSY9mF27DTzuRZ3OCLvLmk"
)

var client *nftstorage.APIClient = nil

func init() {
	if client == nil {
		cfg := nftstorage.NewConfiguration()
		client = nftstorage.NewAPIClient(cfg)
	}
	return
}

func upload() {
	// cfg := nftstorage.NewConfiguration()
	// client := nftstorage.NewAPIClient(cfg)

	ctx := context.Background()
	// filepath := "../../imgs/1.png"
	filepath := "../../imgs/1.json"
	// filepath := "../../imgs/1.gif"
	// filepath := "../../imgs/1.jpg"
	// filepath := "../../imgs/1.mp4"
	// filepath := "../../imgs/1.mp3"
	file, err := os.Open(filepath)
	if err != nil {
		fmt.Fprintf(os.Stderr, "file not found: %v\n", err)
		return
	}

	auth := context.WithValue(ctx, nftstorage.ContextAccessToken, ACCESS_TOKEN)
	resp, r, err := client.NFTStorageAPI.Store(auth).Body(file).Execute()
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error when calling `NFTStorageAPI.Store``: %v\n", err)
		fmt.Fprintf(os.Stderr, "Full HTTP response: %v\n", r)
	}

	fmt.Fprintf(os.Stdout, "Response from `NFTStorageAPI.Store`: %v\n", resp)
}

func list() {
	ctx := context.Background()
	auth := context.WithValue(ctx, nftstorage.ContextAccessToken, ACCESS_TOKEN)
	resp, r, err := client.NFTStorageAPI.List(auth).Limit(100).Execute()
	if err != nil {
		fmt.Fprintf(os.Stderr, "Error when calling `NFTStorageAPI.List``: %v\n", err)
		fmt.Fprintf(os.Stderr, "Full HTTP response: %v\n", r)
	}

	vj, err := resp.MarshalJSON()
	if err != nil {
		fmt.Printf("marshal eror")
		return
	}

	fmt.Fprintf(os.Stdout, "%s", vj)
}

func main() {

	if len(os.Args) > 1 {
		action := os.Args[1]
		switch action {
		case "upload":
			upload()
		case "list":
			list()
		}
	}
}
